package com.jobs.im.gateway.factory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.HasRouteId;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @program: im
 * @ClassName: ReqRateLimiterGatewayFilterFactory
 * @description:
 * @author: Author
 * @create: 2024-02-22 11:15
 * @Version 1.0
 **/
@Component
public class ReqRateLimiterGatewayFilterFactory
    extends AbstractGatewayFilterFactory<ReqRateLimiterGatewayFilterFactory.Config> {
    @Value("${spring.cloud.gateway.log.info:false}")
    private boolean infoLog;
    private Logger requestLogger = LoggerFactory.getLogger("requestLogger");

    @Qualifier("redisTemplate")
    @Resource(name = "redisTemplate")
    public RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> redisScript;

    public ReqRateLimiterGatewayFilterFactory() {
        super(Config.class);
        redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("limit.lua"));
        redisScript.setResultType(Long.class);
    }

    @Override
    public GatewayFilter apply(ReqRateLimiterGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            if (infoLog) {
                requestLogger.info("{}在{}访问了请求：{}", ip,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    exchange.getRequest().getURI().getPath());
            }
            Long number = (Long)redisTemplate.execute(redisScript, Collections.singletonList(ip), config.getPeriod(),
                System.currentTimeMillis(), config.getLimit());
            if (number != null && number.intValue() >= config.getLimit()) {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config implements HasRouteId {
        private String routeId;

        private int period;

        private int limit;
    }
}
