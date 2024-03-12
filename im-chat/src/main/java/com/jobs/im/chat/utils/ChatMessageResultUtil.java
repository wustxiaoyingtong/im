package com.jobs.im.chat.utils;

import java.time.LocalDateTime;

import com.alibaba.fastjson2.JSON;
import com.jobs.im.model.bean.ChatMessageResult;

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
    public static TextWebSocketFrame fail(String message) {
        return new TextWebSocketFrame(JSON.toJSONString(ChatMessageResult.builder().name("system").nickName("系统消息")
            .time(LocalDateTime.now()).message(message).build()));
    }

    public static CloseWebSocketFrame login() {
        return new CloseWebSocketFrame(WebSocketCloseStatus.NORMAL_CLOSURE, "请先登录");
    }

    public static TextWebSocketFrame success(String message) {
        return new TextWebSocketFrame(JSON.toJSONString(ChatMessageResult.builder().name("system").nickName("系统消息")
            .time(LocalDateTime.now()).message(message).build()));
    }

    public static TextWebSocketFrame success(String name, String nickName, String message) {
        return new TextWebSocketFrame(JSON.toJSONString(ChatMessageResult.builder().name(name).nickName(nickName)
            .time(LocalDateTime.now()).message(message).build()));
    }
}
