package com.jobs.im.core.shiro;

import javax.annotation.Resource;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: im
 * @ClassName: ShiroCacheManagerService
 * @description:
 * @author: Author
 * @create: 2024-02-23 11:50
 * @Version 1.0
 **/
@ConditionalOnBean(RedisTemplate.class)
@Service("shiroCacheManagerService")
public class ShiroCacheManagerService implements CacheManager {
    @Value("${shiro.cache.expireTime:7200}")
    private Long expireTime;

    @Resource
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new ShiroRedisCache<K, V>(expireTime, redisTemplate);
    }
}
