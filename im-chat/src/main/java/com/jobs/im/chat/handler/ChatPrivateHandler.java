package com.jobs.im.chat.handler;

import java.util.Date;
import java.util.Objects;

import com.jobs.im.chat.factory.ChatUserFactory;
import com.jobs.im.chat.utils.ChatMessageResultUtil;
import com.jobs.im.feign.dto.ReqSysUserFgDto;
import com.jobs.im.feign.dto.RspSysUserFgDto;
import com.jobs.im.model.bean.ChatMessage;
import com.jobs.im.model.bean.ChatMessageResult;
import com.jobs.im.model.enu.ChatMessageType;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @program: im
 * @ClassName: ChatPrivateHandler
 * @description:
 * @author: Author
 * @create: 2024-03-13 14:58
 * @Version 1.0
 **/
public final class ChatPrivateHandler {
    public static void execute(ChannelHandlerContext ctx, ChatMessage message) {
        if (Objects.isNull(message.getTarget())) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("消息发送失败，请确认发送对象"));
            return;
        }
        RspSysUserFgDto user = ChatUserFactory.getUser(ReqSysUserFgDto.builder().uid(message.getTarget()).build());
        if (Objects.isNull(user)) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("消息发送失败，发送对象不存在"));
            return;
        }
        Channel channel = ChatUserFactory.getChannel(message.getTarget());
        if (Objects.isNull(channel) || !channel.isActive()) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("消息发送失败，对方" + user.getNickname() + "不在线"));
            return;
        }
        RspSysUserFgDto self = ChatUserFactory.getUser(ReqSysUserFgDto.builder().uid(message.getUid()).build());
        ChatMessageResult result = ChatMessageResult.builder().uid(self.getUid()).name(self.getUsername())
            .nickName(self.getNickname()).type(ChatMessageType.PRIVATE.type).target(user.getUid())
            .targetName(user.getNickname()).content(message.getContent()).time(new Date()).build();
        channel.writeAndFlush(ChatMessageResultUtil.success(result));
    }
}
