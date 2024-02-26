package com.jobs.im.rbac.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.exception.ServerException;
import com.jobs.im.core.jwt.JwtUtil;
import com.jobs.im.core.utils.Assert;
import com.jobs.im.core.utils.BeanMapperUtil;
import com.jobs.im.core.utils.PasswordEncoderUtil;
import com.jobs.im.model.bean.SysUser;
import com.jobs.im.model.dto.ReqSysUserLoginDto;
import com.jobs.im.model.dto.RespSysUserLoginDto;
import com.jobs.im.model.enu.SysUserStatus;
import com.jobs.im.rbac.dao.SysUserMapper;
import com.jobs.im.rbac.service.ISysUserLoginService;

/**
 * @program: im
 * @ClassName: SysUserLoginServiceImpl
 * @description:
 * @author: Author
 * @create: 2024-02-23 15:03
 * @Version 1.0
 **/
@Service
public class SysUserLoginServiceImpl implements ISysUserLoginService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public RespSysUserLoginDto login(ReqSysUserLoginDto reqDto) throws UnsupportedEncodingException {
        List<SysUser> sysUsers =
            sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, reqDto.getUsername()));
        Assert.isTrue(CollectionUtils.isNotEmpty(sysUsers), ApiCodeEnum.ACCOUNT_NOT_EXISTS);
        SysUser sysUserDb = sysUsers.stream().findFirst().get();
        if (!SysUserStatus.isAbled(SysUserStatus.valueOf(sysUserDb.getIsDisabled()))) {
            throw new ServerException(ApiCodeEnum.ACCOUNT_FORBIDDEN);
        }
        if (!PasswordEncoderUtil.matches(reqDto.getPassword(), sysUserDb.getPassword())) {
            throw new ServerException(ApiCodeEnum.ACCOUNT_PWD_ERROR);
        }
        RespSysUserLoginDto respSysUserLoginDto = BeanMapperUtil.map(sysUserDb, RespSysUserLoginDto.class);
        String token = JwtUtil.createToken(sysUserDb.getUsername(), sysUserDb.getPassword());
        respSysUserLoginDto.setAccessToken(token);
        return respSysUserLoginDto;
    }
}
