package com.jobs.im.model.bean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ChatMessageResult
 * @description:
 * @author: Author
 * @create: 2024-02-27 16:17
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResult {
    // 发送人uid
    String uid;

    // 发送人name
    String name;

    // 发送人昵称
    String nickName;

    // 消息类型
    Integer type;

    // 消息发送对象，私聊或群聊
    String target;

    // 发送对象名称
    String targetName;

    // 消息内容
    String content;

    // 发送时间
    Date time;
}