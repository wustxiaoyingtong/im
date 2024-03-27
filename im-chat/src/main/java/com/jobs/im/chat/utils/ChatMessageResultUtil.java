package com.jobs.im.chat.utils;

import java.util.Date;

import com.alibaba.fastjson2.JSON;
import com.jobs.im.model.bean.ChatMessageResult;
import com.jobs.im.model.enu.ChatMessageType;

import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;

/**
 * @program: im
 * @ClassName: ChatMessageResultUtil
 * @description:
 * @author: Author
 * @create: 2024-02-27 16:23
 * @Version 1.0
 **/
public final class ChatMessageResultUtil {
    public static CloseWebSocketFrame login() {
        return new CloseWebSocketFrame(WebSocketCloseStatus.NORMAL_CLOSURE, "请先登录");
    }

    public static TextWebSocketFrame fail(String message) {
        return new TextWebSocketFrame(JSON.toJSONString(ChatMessageResult.builder().name("system").nickName("系统消息")
            .type(ChatMessageType.SYSTEM.type).content(message).time(new Date()).build()));
    }

    public static TextWebSocketFrame success(String message) {
        return new TextWebSocketFrame(JSON.toJSONString(ChatMessageResult.builder().name("system").nickName("系统消息")
            .type(ChatMessageType.SYSTEM.type).content(message).time(new Date()).build()));
    }

    public static TextWebSocketFrame success(ChatMessageResult result) {
        return new TextWebSocketFrame(JSON.toJSONString(result));
    }
}
