package com.jobs.im.chat.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobs.im.model.bean.ImChatGroup;

/**
 * 群聊信息表(ImChatGroup)数据库访问层
 *
 * @author wust
 * @since 2024-03-14 16:30:54
 */
@Mapper
public interface ImChatGroupMapper extends BaseMapper<ImChatGroup> {}
