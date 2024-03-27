package com.jobs.im.model.enu;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: im
 * @ClassName: ImChatGroupRole
 * @description:
 * @author: Author
 * @create: 2024-03-22 15:09
 * @Version 1.0
 **/
@AllArgsConstructor
@Getter
public enum ImChatGroupRole {
    COMMON(0, "一般角色"), ADMIN(1, "管理员"), ERROR(-1, "不支持的类型");

    public Integer type;

    public String desc;

    public static ImChatGroupRole valueOf(Integer type) {
        return Arrays.stream(ImChatGroupRole.values()).filter(c -> c.type.equals(type)).findFirst()
            .orElse(ImChatGroupRole.ERROR);
    }
}
