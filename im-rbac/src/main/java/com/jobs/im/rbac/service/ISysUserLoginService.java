package com.jobs.im.rbac.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.jobs.im.core.model.SysUserLogin;
import com.jobs.im.model.dto.ReqSysUserLoginDto;

/**
 * @program: im
 * @ClassName: ISysUserLoginService
 * @description:
 * @author: Author
 * @create: 2024-02-23 15:03
 * @Version 1.0
 **/
public interface ISysUserLoginService {
    SysUserLogin login(ReqSysUserLoginDto reqDto) throws UnsupportedEncodingException;

    void logout(HttpServletRequest request);
}
