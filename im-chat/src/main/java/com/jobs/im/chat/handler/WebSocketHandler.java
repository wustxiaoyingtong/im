package com.jobs.im.chat.handler;

import com.alibaba.fastjson2.JSON;
import com.jobs.im.chat.utils.ChatMessageResultUtil;
import com.jobs.im.model.bean.ChatCommand;
import com.jobs.im.model.enu.ChatCommandType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @program: im
 * @ClassName: WebSocketHandler
 * @description:
 * @author: Author
 * @create: 2024-02-26 23:00
 * @Version 1.0
 **/
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        try {
            System.out.println(frame.text());
            ChatCommand command = JSON.parseObject(frame.text(), ChatCommand.class);
            switch (ChatCommandType.valueOf(command.getCode())) {
                case CONNECTION:
                    ConnectionHandler.execute();
                    break;
                default:
                    ctx.channel().writeAndFlush(ChatMessageResultUtil.fail("不支持的IM命令"));
            }
        } catch (Exception e) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail(e.getMessage()));
        }
    }
}
