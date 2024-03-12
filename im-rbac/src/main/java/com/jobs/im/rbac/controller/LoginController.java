package com.jobs.im.rbac.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobs.im.core.exception.ServerException;
import com.jobs.im.core.model.ApiResult;
import com.jobs.im.model.dto.ReqSysUserLoginDto;
import com.jobs.im.rbac.service.ISysUserLoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @program: im
 * @ClassName: LoginController
 * @description:
 * @author: Author
 * @create: 2024-02-23 14:58
 * @Version 1.0
 **/
@RestController
@RequestMapping("/sys")
@Api(tags = "系统登录")
public class LoginController {
    @Autowired
    private ISysUserLoginService sysUserLoginService;

    @ApiOperation(value = "登录")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult login(@Valid @RequestBody ReqSysUserLoginDto reqDto)
        throws UnsupportedEncodingException, ServerException {
        return ApiResult.success(sysUserLoginService.login(reqDto));
    }

    @ApiOperation(value = "退出")
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult logout(HttpServletRequest request) throws ServerException {
        sysUserLoginService.logout(request);
        return ApiResult.success();
    }
}
