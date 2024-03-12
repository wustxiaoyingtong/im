package com.jobs.im.core.snowflake;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: im
 * @ClassName: SnowFlakeBean
 * @description:
 * @author: Author
 * @create: 2024-03-12 10:30
 * @Version 1.0
 **/
@Component
public class SnowFlakeBean {
    @Value("${snowFlake.dataCenterId:1}")
    long dataCenterId;

    @Value("${snowFlake.machineId:1}")
    long machineId;
}
