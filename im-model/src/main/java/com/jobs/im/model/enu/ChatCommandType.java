package com.jobs.im.model.enu;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: im
 * @ClassName: ChatCommandType
 * @description:
 * @author: Author
 * @create: 2024-02-27 15:42
 * @Version 1.0
 **/
@AllArgsConstructor
@Getter
public enum ChatCommandType {
    CONNECTION(100001, "建立连接"), DISCONNECTION(100002, "断开连接"), CHAT(100003, "聊天"), ERROR(-1, "错误编码");

    public Integer code;

    public String desc;

    public static ChatCommandType valueOf(Integer code) {
        return Arrays.stream(ChatCommandType.values()).filter(c -> c.code.equals(code)).findFirst()
            .orElse(ChatCommandType.ERROR);
    }
}
