package com.jobs.im.chat.handler;

import com.alibaba.fastjson2.JSON;
import com.jobs.im.chat.utils.ChatMessageResultUtil;
import com.jobs.im.model.bean.ChatMessage;
import com.jobs.im.model.enu.ChatMessageType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @program: im
 * @ClassName: ChatHandler
 * @description:
 * @author: Author
 * @create: 2024-03-13 10:33
 * @Version 1.0
 **/
public final class ChatHandler {
    public static void execute(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        try {
            System.out.println(frame.text());
            ChatMessage chatMessage = JSON.parseObject(frame.text(), ChatMessage.class);
            switch (ChatMessageType.valueOf(chatMessage.getType())) {
                case PRIVATE:
                    ChatPrivateHandler.execute(ctx, chatMessage);
                    break;
                case GROUP:
                    ChatGroupHandler.execute(ctx, chatMessage);
                    break;
                default:
                    ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("不支持的聊天类型"));
            }
        } catch (Exception e) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("发送的消息格式错误"));
        }
    }
}
