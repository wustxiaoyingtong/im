package com.jobs.im.chat.service;

import com.jobs.im.model.bean.ImChatMessage;
import com.jobs.im.model.dto.ReqImChatMessageDto;
import com.jobs.im.model.service.BaseService;

/**
 * 聊天消息记录(ImChatMessage)表Service接口
 *
 * @author wust
 * @since 2024-04-12 14:50:15
 */
public interface IImChatMessageService extends BaseService<ImChatMessage, ReqImChatMessageDto> {}
