package com.jobs.im.core.common;

/**
 * @program: im
 * @ClassName: Cst
 * @description:
 * @author: Author
 * @create: 2024-02-22 17:08
 * @Version 1.0
 **/
public interface Cst {
    String MYBATIS_MAPPER_YPE_ALIASES = "com.jobs.im.*.bean.*,com.jobs.im.model.*.*";
    String ACCESS_TOKEN = "Access-Token";
    String REDIS_KEY_SYS_USER = "sys:user:info:%s";
    Long REDIS_KEY_SYS_USER_TIME = 2L;
}
