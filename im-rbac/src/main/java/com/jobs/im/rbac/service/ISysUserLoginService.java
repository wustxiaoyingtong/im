package com.jobs.im.rbac.service;

import java.io.UnsupportedEncodingException;

import com.jobs.im.model.dto.ReqSysUserLoginDto;
import com.jobs.im.model.dto.RespSysUserLoginDto;

/**
 * @program: im
 * @ClassName: ISysUserLoginService
 * @description:
 * @author: Author
 * @create: 2024-02-23 15:03
 * @Version 1.0
 **/
public interface ISysUserLoginService {
    RespSysUserLoginDto login(ReqSysUserLoginDto reqDto) throws UnsupportedEncodingException;
}
