package com.jobs.im.rbac.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobs.im.core.model.ApiResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @program: im
 * @ClassName: TestController
 * @description:
 * @author: Author
 * @create: 2024-02-22 16:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/test")
@Api(tags = "测试")
public class TestController {
    @ApiOperation(value = "测试")
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult test() {
        return ApiResult.success(111);
    }
}
