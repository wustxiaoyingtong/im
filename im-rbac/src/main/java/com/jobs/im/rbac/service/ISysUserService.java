package com.jobs.im.rbac.service;

import com.jobs.im.feign.dto.ReqAuthenticationInfo;
import com.jobs.im.feign.dto.RspAuthenticationInfo;
import com.jobs.im.model.bean.SysUser;
import com.jobs.im.model.dto.ReqSysUserDto;
import com.jobs.im.model.service.BaseService;

/**
 * 系统用户表(SpSysUser)表Service接口
 *
 * @author wust
 * @since 2024-02-23 14:00:45
 */
public interface ISysUserService extends BaseService<SysUser, ReqSysUserDto> {
    SysUser getUserByName(ReqSysUserDto reqDto);

    RspAuthenticationInfo getAuthenticationInfo(ReqAuthenticationInfo reqAuthenticationInfo);
}
