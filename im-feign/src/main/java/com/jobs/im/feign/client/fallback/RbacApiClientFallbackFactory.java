package com.jobs.im.feign.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jobs.im.feign.client.RbacApiClient;
import com.jobs.im.feign.dto.ReqAuthenticationInfo;
import com.jobs.im.feign.dto.ReqSysUserFgDto;
import com.jobs.im.feign.dto.RspAuthenticationInfo;
import com.jobs.im.feign.dto.RspSysUserFgDto;

import feign.hystrix.FallbackFactory;

/**
 * @program: im
 * @ClassName: RbacApiClientFallbackFactory
 * @description: 降级处理类
 * @author: Author
 * @create: 2024-02-24 09:43
 * @Version 1.0
 **/
@Component
public class RbacApiClientFallbackFactory implements FallbackFactory<RbacApiClient> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public RbacApiClient create(Throwable throwable) {
        log.error("RbacApiClient 调用异常，触发降级 -> {}", throwable.getMessage(), throwable);
        return new RbacApiClient() {
            @Override
            public RspAuthenticationInfo getAuthenticationInfo(ReqAuthenticationInfo reqAuthenticationInfo) {
                return null;
            }

            @Override
            public RspSysUserFgDto getUser(ReqSysUserFgDto req) {
                return null;
            }
        };
    }
}
