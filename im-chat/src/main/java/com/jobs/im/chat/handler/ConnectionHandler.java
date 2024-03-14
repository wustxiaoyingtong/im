package com.jobs.im.chat.handler;

import com.jobs.im.chat.factory.ChatUserFactory;
import com.jobs.im.chat.utils.ChatMessageResultUtil;
import com.jobs.im.model.bean.ChatCommand;

import io.netty.channel.ChannelHandlerContext;

/**
 * @program: im
 * @ClassName: ConnectionHandler
 * @description:
 * @author: Author
 * @create: 2024-02-27 16:11
 * @Version 1.0
 **/
public final class ConnectionHandler {
    public static void execute(ChannelHandlerContext ctx, ChatCommand command) {
        ChatUserFactory.putChannel(command.getUid(), ctx.channel());
        ctx.channel().writeAndFlush(ChatMessageResultUtil.success("上线成功"));
    }
}