package com.jobs.im.core.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: im
 * @ClassName: RedisConfig
 * @description:
 * @author: Author
 * @create: 2024-02-22 11:32
 * @Version 1.0
 **/
@ConditionalOnExpression("${redis.enabled:false}")
@Configuration
public class RedisConfig {
    @Value("${redis.host:127.0.0.1}")
    private String host;
    @Value("${redis.port:6379}")
    private Integer port;
    @Value("${redis.password:'root'}")
    private String password;
    @Value("${redis.database:1}")
    private Integer database;
    @Value("${redis.lockdatabase:2}")
    private Integer lockdatabase;
    @Value("${redis.pool.maxTotal:30000}")
    private Integer maxTotal;
    @Value("${redis.pool.maxIdle:10000}")
    private Integer maxIdle;
    @Value("${redis.pool.maxWait:1500}")
    private Integer maxWaitMillis;
    @Value("${redis.pool.testOnBorrow:true}")
    private Boolean testOnBorrow;
    @Value("${redis.pool.testOnReturn:true}")
    private Boolean testOnReturn;

    /**
     * Description: JedisPoolConfig
     *
     * @param
     * @return JedisPoolConfig
     * @throws
     * @author Author
     * @date 2024/2/22 11:33
     **/
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        return jedisPoolConfig;
    }

    /**
     * Description: JedisConnectionFactory
     *
     * @param
     * @return JedisConnectionFactory
     * @throws
     * @author Author
     * @date 2024/2/22 11:35
     **/
    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setDatabase(database);
        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return jedisConnectionFactory;
    }

    /**
     * Description: RedisTemplate
     *
     * @param jedisConnectionFactory
     * @return String
     * @throws
     * @author Author
     * @date 2024/2/22 11:36
     **/
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object>
        redisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        // String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson的序列化方式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value采用jackson的序列化方式
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "lockRedisTemplate")
    public RedisTemplate<String, Object> lockRedisTemplate() {
        // 为了开发方便，采用<String,Object>
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setDatabase(lockdatabase);
        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        // String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson的序列化方式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value采用jackson的序列化方式
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
