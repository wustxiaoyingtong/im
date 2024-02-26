package com.jobs.im.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.jobs.im.core.model.SysUserWeb;

/**
 * @program: im
 * @ClassName: LoginUserContextHolder
 * @description: 缓存用户登录信息
 * @author: Author
 * @create: 2024-02-26 17:44
 * @Version 1.0
 **/
public final class LoginUserContextHolder {
    private static final ThreadLocal<SysUserWeb> CONTEXT = new TransmittableThreadLocal<>();

    public static void setUser(SysUserWeb userWeb) {
        CONTEXT.set(userWeb);
    }

    public static SysUserWeb getUser() {
        return CONTEXT.get();
    }

    public static void remove() {
        CONTEXT.remove();
    }
}
