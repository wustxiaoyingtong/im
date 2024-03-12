package com.jobs.im.core.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @program: im
 * @ClassName: RedisService
 * @description:
 * @author: Author
 * @create: 2024-03-12 17:22
 * @Version 1.0
 **/
@Component
public class RedisService {
    @Qualifier("redisTemplate")
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * Description: 判断key是否存在
     *
     * @param key
     * @return Boolean
     * @throws
     * @author Author
     * @date 2024/3/12 17:26
     **/
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Description: set(String,T)
     *
     * @param key
     * @param value
     * @return
     * @throws
     * @author Author
     * @date 2024/3/12 17:23
     **/
    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Description: set(K,T)
     *
     * @param key
     * @param value
     * @return
     * @throws
     * @author Author
     * @date 2024/3/12 17:24
     **/
    public <K, T> void set(K key, T value) {
        set(key.toString(), value);
    }

    /**
     * Description: set(String,T)带过期时间
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     * @return
     * @throws
     * @author Author
     * @date 2024/3/12 17:24
     **/
    public <T> void set(String key, T value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * Description: 设置过期时间
     *
     * @param key
     * @param timeout
     * @param unit
     * @return boolean
     * @throws
     * @author Author
     * @date 2024/3/12 17:25
     **/
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * Description: 根据key获取数据
     *
     * @param key
     * @return T
     * @throws
     * @author Author
     * @date 2024/3/12 17:27
     **/
    public <T> T get(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * Description: 根据key对象获取数据
     *
     * @param key
     * @return T
     * @throws
     * @author Author
     * @date 2024/3/12 17:27
     **/
    public <K, T> T get(K key) {
        return get(key.toString());
    }

    /**
     * Description: 删除key
     *
     * @param key
     * @return boolean
     * @throws
     * @author Author
     * @date 2024/3/12 17:28
     **/
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
