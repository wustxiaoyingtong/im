package com.jobs.im.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.jobs.im.feign.client.fallback.RbacApiClientFallbackFactory;
import com.jobs.im.feign.common.Cst;
import com.jobs.im.feign.dto.ReqAuthenticationInfo;
import com.jobs.im.feign.dto.RspAuthenticationInfo;

/**
 * @program: im
 * @ClassName: RbacApiClient
 * @description:
 * @author: Author
 * @create: 2024-02-24 09:40
 * @Version 1.0
 **/
@FeignClient(name = Cst.IM_RBAC, path = Cst.IM_RBAC, fallbackFactory = RbacApiClientFallbackFactory.class)
public interface RbacApiClient {
    @PostMapping(value = "/feign/getAuthenticationInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    RspAuthenticationInfo getAuthenticationInfo(ReqAuthenticationInfo reqAuthenticationInfo);
}
