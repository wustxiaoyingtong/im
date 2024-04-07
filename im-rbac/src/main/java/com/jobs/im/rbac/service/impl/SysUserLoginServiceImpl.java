package com.jobs.im.rbac.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jobs.im.core.common.Cst;
import com.jobs.im.core.config.RedisService;
import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.exception.ServerException;
import com.jobs.im.core.jwt.JwtUtil;
import com.jobs.im.core.model.SysUserLogin;
import com.jobs.im.core.snowflake.SnowFlake;
import com.jobs.im.core.utils.Assert;
import com.jobs.im.core.utils.BeanMapperUtil;
import com.jobs.im.core.utils.PasswordEncoderUtil;
import com.jobs.im.model.bean.SysUser;
import com.jobs.im.model.dto.ReqSysUserDto;
import com.jobs.im.model.dto.ReqSysUserLoginDto;
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
    @Resource
    private RedisService redisService;

    @Override
    public SysUserLogin login(ReqSysUserLoginDto reqDto) throws UnsupportedEncodingException, ServerException {
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
        SysUserLogin sysUserLogin = BeanMapperUtil.map(sysUserDb, SysUserLogin.class);
        String token = JwtUtil.createToken(sysUserDb.getUsername(), sysUserDb.getPassword());
        sysUserLogin.setAccessToken(token);
        redisService.set(String.format(Cst.REDIS_KEY_SYS_USER, sysUserLogin.getUsername()), sysUserLogin,
            Cst.REDIS_KEY_SYS_USER_TIME, TimeUnit.DAYS);
        return sysUserLogin;
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = request.getHeader(Cst.ACCESS_TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new ServerException(ApiCodeEnum.TOKEN_EXPIRE);
        }
        String userName = JwtUtil.getUserName(token);
        if (StringUtils.isBlank(userName)) {
            throw new ServerException(ApiCodeEnum.TOKEN_EXPIRE);
        }
        List<SysUser> sysUsers =
            sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, userName));
        Assert.isTrue(CollectionUtils.isNotEmpty(sysUsers), ApiCodeEnum.TOKEN_EXPIRE);
        SysUser sysUserDb = sysUsers.stream().findFirst().get();
        redisService.delete(String.format(Cst.REDIS_KEY_SYS_USER, sysUserDb.getUsername()));
    }

    @Override
    public void signUp(ReqSysUserDto reqDto) throws RuntimeException {
        Assert.isTrue(StringUtils.isNotBlank(reqDto.getPassword()), ApiCodeEnum.PARAM_ERROR);
        List<SysUser> list =
            sysUserMapper.selectList(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, reqDto.getUsername()));
        Assert.isTrue(CollectionUtils.isEmpty(list), ApiCodeEnum.USERNAME_DUPLICATED);
        reqDto.setUid(SnowFlake.next());
        reqDto.setPassword(PasswordEncoderUtil.encode(reqDto.getPassword()));
        reqDto.setIsDisabled(SysUserStatus.SYS_USER_IS_ENABLED.value);
        sysUserMapper.insert(BeanMapperUtil.map(reqDto, SysUser.class));
    }
}