package com.jobs.im.file.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobs.im.model.bean.ImFile;

/**
 * 文件信息表(ImFile)数据库访问层
 *
 * @author wust
 * @since 2024-03-29 16:35:21
 */
@Mapper
public interface ImFileMapper extends BaseMapper<ImFile> {}
