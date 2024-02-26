package com.jobs.im.core.shiro;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ShiroRedisCache
 * @description:
 * @author: Author
 * @create: 2024-02-23 11:51
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private Long expireTime;// 缓存的超时时间，单位为s

    private RedisTemplate<K, V> redisTemplate;// 通过构造方法注入该对象

    public ShiroRedisCache(RedisTemplate<K, V> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    /**
     * 通过key来获取对应的缓存对象 通过源码我们可以发现，shiro需要的key的类型为Object，V的类型为AuthorizationInfo对象
     */
    @Override
    public V get(K key) throws CacheException {
        if (key instanceof SimplePrincipalCollection) {
            // SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection(
            // JwtUtils.getUserId(((SimplePrincipalCollection)key).getPrimaryPrincipal().toString()),
            // AuthRealm.class.getName());
            // key = (K)simplePrincipalCollection;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 将权限信息加入缓存中
     */
    @Override
    public V put(K key, V value) throws CacheException {
        if (key instanceof SimplePrincipalCollection) {
            // SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection(
            // JwtUtils.getUserId(((SimplePrincipalCollection)key).getPrimaryPrincipal().toString()),
            // AuthRealm.class.getName());
            // key = (K)simplePrincipalCollection;
        }
        redisTemplate.opsForValue().set(key, value, this.expireTime, TimeUnit.SECONDS);
        return value;
    }

    /**
     * 将权限信息从缓存中删除
     */
    @Override
    public V remove(K key) throws CacheException {
        V v = redisTemplate.opsForValue().get(key);
        redisTemplate.opsForValue().getOperations().delete(key);
        return v;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
