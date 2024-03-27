package com.jobs.im.chat.handler;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;

import com.jobs.im.chat.factory.ChatGroupFactory;
import com.jobs.im.chat.factory.ChatUserFactory;
import com.jobs.im.chat.utils.ChatMessageResultUtil;
import com.jobs.im.feign.dto.ReqSysUserFgDto;
import com.jobs.im.feign.dto.RspSysUserFgDto;
import com.jobs.im.model.bean.ChatMessage;
import com.jobs.im.model.bean.ChatMessageResult;
import com.jobs.im.model.enu.ChatMessageType;
import com.jobs.im.model.vo.ImChatGroupVo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @program: im
 * @ClassName: ChatNoticeHandler
 * @description:
 * @author: Author
 * @create: 2024-03-25 11:34
 * @Version 1.0
 **/
public final class ChatNoticeHandler {
    public static void execute(ChannelHandlerContext ctx, ChatMessage message) {
        if (Objects.isNull(message.getTarget())) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("消息发送失败，请确认发送群聊对象"));
            return;
        }
        ImChatGroupVo vo = ChatGroupFactory.getGroup(message.getTarget());
        if (Objects.isNull(vo)) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("消息发送失败，发送群聊对象不存在"));
            return;
        }
        if (CollectionUtils.isEmpty(vo.getRels())) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("消息发送失败，群聊没有成员"));
            return;
        }
        RspSysUserFgDto self = ChatUserFactory.getUser(ReqSysUserFgDto.builder().uid(message.getUid()).build());
        if (!vo.getRels().stream().filter(v -> v.getUserId().equals(self.getId())).findAny().isPresent()) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("消息发送失败，你不是改群成员"));
            return;
        }
        ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        vo.getRels().stream().filter(v -> !v.getUserId().equals(self.getId())).forEach(v -> {
            RspSysUserFgDto user = ChatUserFactory.getUser(ReqSysUserFgDto.builder().id(v.getUserId()).build());
            if (Objects.isNull(user)) {
                return;
            }
            Channel channel = ChatUserFactory.getChannel(user.getUid());
            if (Objects.isNull(channel) || !channel.isActive()) {
                return;
            }
            channels.add(channel);
        });
        ChatMessageResult result = ChatMessageResult.builder().uid(self.getUid()).name(self.getUsername())
            .nickName(self.getNickname()).type(ChatMessageType.NOTICE.type).target(vo.getUid()).targetName(vo.getName())
            .content(message.getContent()).time(new Date()).build();
        channels.writeAndFlush(ChatMessageResultUtil.success(result));
    }
}
