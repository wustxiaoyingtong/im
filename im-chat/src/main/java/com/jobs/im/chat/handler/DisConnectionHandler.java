package com.jobs.im.chat.handler;

import com.jobs.im.chat.factory.ChatUserFactory;
import com.jobs.im.chat.utils.ChatMessageResultUtil;
import com.jobs.im.model.bean.ChatCommand;

import io.netty.channel.ChannelHandlerContext;

/**
 * @program: im
 * @ClassName: DisConnectionHandler
 * @description:
 * @author: Author
 * @create: 2024-03-13 15:26
 * @Version 1.0
 **/
public final class DisConnectionHandler {
    public static void execute(ChannelHandlerContext ctx, ChatCommand command) {
        ChatUserFactory.delChannel(command.getUid());
        ctx.channel().writeAndFlush(ChatMessageResultUtil.success("下线成功"));
    }
}
