package com.jobs.im.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jobs.im.core.common.Cst;
import com.jobs.im.core.context.LoginUserContextHolder;
import com.jobs.im.core.jwt.JwtUtil;
import com.jobs.im.core.model.SysUserWeb;

/**
 * @program: im
 * @ClassName: WebInterceptor
 * @description:
 * @author: Author
 * @create: 2024-02-22 18:08
 * @Version 1.0
 **/
public class WebInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String token = request.getHeader(Cst.ACCESS_TOKEN);
        if (StringUtils.isNotEmpty(token)) {
            LoginUserContextHolder
                .setUser(SysUserWeb.builder().acccessToken(token).userName(JwtUtil.getUserName(token)).build());
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        // 调用结束后删除上下文中的用户信息
        LoginUserContextHolder.remove();
    }
}
