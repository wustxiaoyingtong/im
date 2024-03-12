package com.jobs.im.chat.handler;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.jobs.im.chat.factory.ChatUserFactory;
import com.jobs.im.chat.utils.ChatMessageResultUtil;
import com.jobs.im.core.jwt.JwtUtil;
import com.jobs.im.core.utils.SysUserUtil;
import com.jobs.im.feign.dto.ReqSysUserFgDto;
import com.jobs.im.feign.dto.RspSysUserFgDto;
import com.jobs.im.model.bean.ChatCommand;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @program: im
 * @ClassName: PermissionWebSocketHandler
 * @description:
 * @author: Author
 * @create: 2024-03-12 10:56
 * @Version 1.0
 **/
@Component
@ChannelHandler.Sharable
public class PermissionWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        try {
            ChatCommand command = JSON.parseObject(frame.text(), ChatCommand.class);
            if (StringUtils.isBlank(command.getToken()) || Objects.isNull(command.getUid())) {
                login(ctx);
                return;
            }
            Date expiresAt = JwtUtil.getExpiresAt(command.getToken());
            if (Objects.isNull(expiresAt) || expiresAt.before(new Date())) {
                login(ctx);
                return;
            }
            // 校验token
            if (!JwtUtil.verify(command.getToken())) {
                login(ctx);
                return;
            }
            // 校验token的用户和uid查询出的用户名是否一致
            RspSysUserFgDto user = ChatUserFactory.getUser(ReqSysUserFgDto.builder().uid(command.getUid()).build());
            if (Objects.isNull(user) || !user.getUsername().equals(JwtUtil.getUserName(command.getToken()))) {
                login(ctx);
                return;
            }
            if (!SysUserUtil.hasUser(user.getUsername())) {
                login(ctx);
                return;
            }
            ctx.fireChannelRead(frame.retain());
        } catch (Exception e) {
            ctx.channel().writeAndFlush(ChatMessageResultUtil.fail(e.getMessage()));
        }
    }

    private void login(ChannelHandlerContext ctx) {
        ctx.channel().writeAndFlush(ChatMessageResultUtil.login());
    }
}
