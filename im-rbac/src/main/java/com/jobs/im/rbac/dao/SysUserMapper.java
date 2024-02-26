package com.jobs.im.rbac.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobs.im.model.bean.SysUser;

/**
 * 系统用户表(SpSysUser)数据库访问层
 *
 * @author wust
 * @since 2024-02-23 14:00:45
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {}
