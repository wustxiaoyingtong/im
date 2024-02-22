package com.jobs.im.rbac.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobs.im.core.model.ApiResult;

/**
 * @program: im
 * @ClassName: HealthController
 * @description:
 * @author: Author
 * @create: 2024-02-22 16:24
 * @Version 1.0
 **/
@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult check() {
        return ApiResult.success();
    }
}
