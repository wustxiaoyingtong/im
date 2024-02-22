package com.jobs.im.gateway.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobs.im.core.model.ApiResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @program: im
 * @ClassName: FallbackController
 * @description:
 * @author: Author
 * @create: 2024-02-22 11:38
 *
 * @Version 1.0
 **/
@RestController
@RequestMapping("/server")
@Api(tags = "服务请求降级")
public class FallbackController {
    @ApiOperation(value = "服务请求降级")
    @GetMapping(value = "/serverFallback", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult fallback() {
        return ApiResult.fail("Service is unavailable, try again later!");
    }
}
