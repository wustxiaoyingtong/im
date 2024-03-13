package com.jobs.im.chat.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jobs.im.core.config.FeignBeanAgent;
import com.jobs.im.core.context.SpringContextHolder;
import com.jobs.im.feign.dto.ReqSysUserFgDto;
import com.jobs.im.feign.dto.RspSysUserFgDto;

import io.netty.channel.Channel;

/**
 * @program: im
 * @ClassName: ChatUserFactory
 * @description:
 * @author: Author
 * @create: 2024-03-12 09:44
 * @Version 1.0
 **/
public final class ChatUserFactory {
    private static Map<Long, Channel> USERS = new ConcurrentHashMap<>();

    public static void putChannel(Long uid, Channel channel) {
        USERS.put(uid, channel);
    }

    public static Channel getChannel(Long uid) {
        return USERS.get(uid);
    }

    public static void delChannel(Long uid) {
        USERS.remove(uid);
    }

    public static RspSysUserFgDto getUser(ReqSysUserFgDto req) {
        FeignBeanAgent feignBeanAgent = SpringContextHolder.getBean("feignBeanAgent");
        return feignBeanAgent.getRbacApiClient().getUser(req);
    }
}
