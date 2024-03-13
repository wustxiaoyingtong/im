package com.jobs.im.core.context;

import java.util.Optional;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.jobs.im.core.model.SysUserLogin;

/**
 * @program: im
 * @ClassName: SysUserContextHolder
 * @description:
 * @author: Author
 * @create: 2024-03-13 16:57
 * @Version 1.0
 **/
public final class SysUserContextHolder {
    private static final ThreadLocal<SysUserLogin> USER = new TransmittableThreadLocal<>();

    public static void setUser(SysUserLogin sysUserLogin) {
        USER.set(sysUserLogin);
    }

    public static SysUserLogin getUser() {
        return USER.get();
    }

    public static void delUser() {
        USER.remove();
    }

    public static Integer getUserId() {
        return Optional.ofNullable(getUser()).map(SysUserLogin::getId).orElse(null);
    }

    public static Long getUserUid() {
        return Optional.ofNullable(getUser()).map(SysUserLogin::getUid).orElse(null);
    }

    public static String getUserName() {
        return Optional.ofNullable(getUser()).map(SysUserLogin::getUsername).orElse(null);
    }
}