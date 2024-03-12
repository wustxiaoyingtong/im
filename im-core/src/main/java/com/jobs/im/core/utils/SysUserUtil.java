package com.jobs.im.core.utils;

import com.jobs.im.core.common.Cst;
import com.jobs.im.core.config.RedisService;
import com.jobs.im.core.context.SpringContextHolder;
import com.jobs.im.core.model.SysUserLogin;

/**
 * @program: im
 * @ClassName: SysUserUtil
 * @description:
 * @author: Author
 * @create: 2024-03-12 17:54
 * @Version 1.0
 **/
public final class SysUserUtil {
    /**
     * Description: 从redis中获取登录的用户信息
     *
     * @param userName
     * @return SysUserLogin
     * @throws
     * @author Author
     * @date 2024/3/12 18:05
     **/
    public static SysUserLogin getUser(String userName) {
        RedisService redisService = SpringContextHolder.getBean("redisService");
        return redisService.get(String.format(Cst.REDIS_KEY_SYS_USER, userName));
    }

    /**
     * Description: 判断redis中是否存在用户信息
     *
     * @param userName
     * @return Boolean
     * @throws
     * @author Author
     * @date 2024/3/12 18:07
     **/
    public static Boolean hasUser(String userName) {
        RedisService redisService = SpringContextHolder.getBean("redisService");
        return redisService.hasKey(String.format(Cst.REDIS_KEY_SYS_USER, userName));
    }
}
