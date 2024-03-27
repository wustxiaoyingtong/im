package com.jobs.im.model.enu;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: im
 * @ClassName: ChatMessageType
 * @description:
 * @author: Author
 * @create: 2024-03-13 14:54
 * @Version 1.0
 **/
@AllArgsConstructor
@Getter
public enum ChatMessageType {
    SYSTEM(0, "系统"), PRIVATE(1, "私聊"), GROUP(2, "群聊"), NOTICE(3, "通知"), ERROR(-1, "不支持的类型");

    public Integer type;

    public String desc;

    public static ChatMessageType valueOf(Integer type) {
        return Arrays.stream(ChatMessageType.values()).filter(c -> c.type.equals(type)).findFirst()
            .orElse(ChatMessageType.ERROR);
    }
}
