package com.jobs.im.chat.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobs.im.model.bean.ImChatMessage;

/**
 * 聊天消息记录(ImChatMessage)数据库访问层
 *
 * @author wust
 * @since 2024-04-12 14:50:15
 */
@Mapper
public interface ImChatMessageMapper extends BaseMapper<ImChatMessage> {}
