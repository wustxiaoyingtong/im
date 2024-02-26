package com.jobs.im.model.enu;

import java.util.Arrays;

import lombok.AllArgsConstructor;

/**
 * @program: im
 * @ClassName: SysUserStatus
 * @description:
 * @author: Author
 * @create: 2024-02-23 16:22
 * @Version 1.0
 **/
@AllArgsConstructor
public enum SysUserStatus {
    SYS_USER_IS_ENABLED(0, "启用"), SYS_USER_IS_DISABLED(1, "禁用");

    public int value;

    public String desc;

    public static SysUserStatus valueOf(int value) {
        return Arrays.stream(SysUserStatus.values()).filter(s -> s.value == value).findAny().orElse(null);
    }

    public static boolean isAbled(SysUserStatus status) {
        return SYS_USER_IS_ENABLED.equals(status);
    }
}
