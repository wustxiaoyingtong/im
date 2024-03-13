package com.jobs.im.rbac.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jobs.im.core.common.Cst;
import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.jwt.JwtUtil;
import com.jobs.im.core.model.PageInfo;
import com.jobs.im.core.model.SysUserLogin;
import com.jobs.im.core.snowflake.SnowFlake;
import com.jobs.im.core.utils.Assert;
import com.jobs.im.core.utils.BeanMapperUtil;
import com.jobs.im.core.utils.PasswordEncoderUtil;
import com.jobs.im.core.utils.SysUserUtil;
import com.jobs.im.feign.dto.ReqAuthenticationInfo;
import com.jobs.im.feign.dto.RspAuthenticationInfo;
import com.jobs.im.model.bean.SysUser;
import com.jobs.im.model.dto.ReqSysUserDto;
import com.jobs.im.model.enu.SysUserStatus;
import com.jobs.im.rbac.dao.SysUserMapper;
import com.jobs.im.rbac.service.ISysUserService;

/**
 * 系统用户表(SpSysUser)表Service实现类
 *
 * @author wust
 * @since 2024-02-23 14:39:57
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl implements ISysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public int add(ReqSysUserDto reqSysUserDto) throws RuntimeException {
        List<SysUser> list = sysUserMapper
            .selectList(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, reqSysUserDto.getUsername()));
        Assert.isTrue(CollectionUtils.isEmpty(list), ApiCodeEnum.USERNAME_DUPLICATED);
        reqSysUserDto.setUid(SnowFlake.next());
        reqSysUserDto.setPassword(PasswordEncoderUtil.encode(Cst.PASSWD_DEFAULT));
        reqSysUserDto.setIsDisabled(SysUserStatus.SYS_USER_IS_ENABLED.value);
        return sysUserMapper.insert(BeanMapperUtil.map(reqSysUserDto, SysUser.class));
    }

    @Override
    public int delete(Serializable id) throws RuntimeException {
        SysUser sysUser = sysUserMapper.selectById(id);
        Assert.notNull(sysUser, "当前记录不存在");
        return sysUserMapper.deleteById(id);
    }

    @Override
    public SysUser detail(Serializable id) throws RuntimeException {
        return sysUserMapper.selectById(id);
    }

    @Override
    public int update(ReqSysUserDto reqSysUserDto) throws RuntimeException {
        SysUser sysUser = sysUserMapper.selectById(reqSysUserDto.getId());
        Assert.notNull(sysUser, "当前记录不存在");
        return sysUserMapper.updateById(BeanMapperUtil.map(reqSysUserDto, SysUser.class));
    }

    @Override
    public PageInfo<SysUser> queryPage(ReqSysUserDto reqSysUserDto) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery().orderByDesc(SysUser::getCreateAt);
        return query(sysUserMapper, wrapper, reqSysUserDto);
    }

    @Override
    public List<SysUser> query(ReqSysUserDto reqSysUserDto) {
        return null;
    }

    @Override
    public SysUser getUser(ReqSysUserDto reqDto) {
        List<SysUser> sysUsers = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
            .eq(StringUtils.isNotBlank(reqDto.getUsername()), SysUser::getUsername, reqDto.getUsername())
            .eq(!Objects.isNull(reqDto.getUid()), SysUser::getUid, reqDto.getUid()));
        Assert.isTrue(CollectionUtils.isNotEmpty(sysUsers), ApiCodeEnum.ACCOUNT_NOT_EXISTS);
        return sysUsers.stream().findFirst().get();
    }

    @Override
    public RspAuthenticationInfo getAuthenticationInfo(ReqAuthenticationInfo reqAuthenticationInfo) {
        String accessToken = reqAuthenticationInfo.getAccessToken();
        String userName = JwtUtil.getUserName(accessToken);
        if (StringUtils.isEmpty(userName)) {
            return RspAuthenticationInfo.builder().success(false).message("凭证解析失败").build();
        }
        Date expiresAt = JwtUtil.getExpiresAt(accessToken);
        if (Objects.isNull(expiresAt) || expiresAt.before(new Date())) {
            return RspAuthenticationInfo.builder().success(false).message("凭证已经过期").build();
        }
        List<SysUser> sysUsers =
            sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, userName));
        if (CollectionUtils.isEmpty(sysUsers)) {
            return RspAuthenticationInfo.builder().success(false).message("凭证账户不存在").build();
        }
        if (!SysUserUtil.hasUser(userName)) {
            return RspAuthenticationInfo.builder().success(false).message("账号已经登出").build();
        }
        SysUserLogin user = SysUserUtil.getUser(userName);
        if (!user.getAccessToken().equals(accessToken)) {
            return RspAuthenticationInfo.builder().success(false).message("凭证验证失败").build();
        }
        SysUser sysUser = sysUsers.stream().findFirst().get();
        if (!SysUserStatus.isAbled(SysUserStatus.valueOf(sysUser.getIsDisabled()))) {
            return RspAuthenticationInfo.builder().success(false).message("凭证账户不可用").build();
        }
        // 校验token
        if (!JwtUtil.verify(accessToken)) {
            return RspAuthenticationInfo.builder().success(false).message("凭证验证失败").build();
        }
        return RspAuthenticationInfo.builder().success(true).build();
    }
}
