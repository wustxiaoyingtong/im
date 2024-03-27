package com.jobs.im.chat.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobs.im.model.bean.ImChatGroupRel;

/**
 * 群聊-用户关联表(ImChatGroupRel)数据库访问层
 *
 * @author wust
 * @since 2024-03-22 11:51:26
 */
@Mapper
public interface ImChatGroupRelMapper extends BaseMapper<ImChatGroupRel> {}
