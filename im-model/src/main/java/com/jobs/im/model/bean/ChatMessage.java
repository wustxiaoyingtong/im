package com.jobs.im.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: ChatMessage
 * @description:
 * @author: Author
 * @create: 2024-03-13 10:38
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends ChatCommand {
    // 消息类型
    Integer type;

    // 消息发送对象，私聊或群聊
    String target;

    // 消息内容
    String content;
}
