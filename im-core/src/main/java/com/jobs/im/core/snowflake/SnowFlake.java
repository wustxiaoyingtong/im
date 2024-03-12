package com.jobs.im.core.snowflake;

import com.jobs.im.core.context.SpringContextHolder;

/**
 * @program: im
 * @ClassName: SnowFlake
 * @description:
 * @author: Author
 * @create: 2024-03-12 10:04
 * @Version 1.0
 **/
public class SnowFlake {
    /**
     * 起始的时间戳（可设置当前时间之前的邻近时间）
     * <p>
     * 1704038400000L（2024/01/01 00:00:00）
     */
    private final static long START_STAMP = 1704038400000L;

    /**
     * 序列号占用的位数（第四部分）
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * 机器标识占用的位数（第三部分之一）
     */
    private final static long MACHINE_BIT = 5;

    /**
     * 数据中心占用的位数（第三部分之二）
     */
    private final static long DATA_CENTER_BIT = 5;

    /**
     * 第三部分的最大值（31D）
     */
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    /**
     * 第四部分的最大值(4095D)
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 第三部分需要向左移位的位移（12）及（12 + 5）
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;

    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    /**
     * 第二部分需要向左移位的位移（12 + 5 + 5）
     */
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    /**
     * 数据中心 ID(0~31)，5 bit
     */
    private final long dataCenterId;

    /**
     * 工作机器 ID(0~31)，5 bit
     */
    private final long machineId;

    /**
     * 相同毫秒内需要自增的序列号(0~4095)，12 bit
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastStamp = -1L;

    /**
     * 初始化 类，填入 数据中心 ID、工作机器 ID
     *
     * @param dataCenterId
     *            数据中心 ID
     * @param machineId
     *            工作机器 ID
     */
    private SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId < 0 || dataCenterId > MAX_DATA_CENTER_NUM) {
            throw new IllegalArgumentException(
                "dataCenterId can't be " + "greater than MAX_DATA_CENTER_NUM " + "or less than 0");
        }
        if (machineId < 0 || machineId > MAX_MACHINE_NUM) {
            throw new IllegalArgumentException("machineId can't be " + "greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    public static SnowFlake init(long dataCenterId, long machineId) {
        return new SnowFlake(dataCenterId, machineId);
    }

    /**
     * 生成 下一个 ID
     * <p>
     * 添加了 同步锁
     *
     * @return ID
     */
    public synchronized long nextId() {
        long currStamp = getCurrentTimestamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.Refusing to generate id.");
        }
        // 确定好 时间戳 和 序列号
        if (currStamp == lastStamp) {
            // 相同服务器在相同毫秒内创建 ID 时，序列号自增
            // 相同服务器：表示 第三部分（数据中心 ID、工作机器 ID）相同
            // 相同毫秒：表示 第二部分（时间戳）相同
            // 所以需要用 第四部分 来区分 ID，设置为 自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0L) {
                // 序列号达到最大了，就阻塞到下一个毫秒，获得新的时间戳
                currStamp = getNextTimestamp();
            }
        } else {
            // 不同的毫秒时，表示是 某个毫秒的第一个 ID
            // 所以 序列号置为零
            sequence = 0L;
        }

        // 更新 上一次生成 ID 的时间戳
        lastStamp = currStamp;

        // 通过 移位、和或运算符 将四个部分拼接到一起，组成 64 bit的 ID
        // 就是把 第一、二、三部分的有效数值移动 n 位，然后用 '或' 运算进行拼接
        // 或运算（|）是用于二进制数值间运算的，相同位均为 0，结果位才为 0，否则为 1。
        return (currStamp - START_STAMP) << TIMESTAMP_LEFT | dataCenterId << DATA_CENTER_LEFT
            | machineId << MACHINE_LEFT | sequence;
    }

    public static long next() {
        SnowFlakeBean snowFlakeBean = SpringContextHolder.getBean("snowFlakeBean");
        return SnowFlake.init(snowFlakeBean.dataCenterId, snowFlakeBean.machineId).nextId();
    }

    /**
     * 获取 下一个 ID 的时间戳
     * <p>
     * 下一个 ID 的时间戳一定要 大于等于 上一个 ID 的时间戳
     *
     * @return 可用的时间戳
     */
    private long getNextTimestamp() {
        long stamp = getCurrentTimestamp();
        while (stamp <= lastStamp) {
            stamp = getCurrentTimestamp();
        }
        return stamp;
    }

    /**
     * 获取 当前时间戳
     *
     * @return 当前时间戳
     */
    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}
