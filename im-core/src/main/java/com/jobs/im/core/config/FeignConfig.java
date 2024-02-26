package com.jobs.im.core.config;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jobs.im.core.common.Cst;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @program: im
 * @ClassName: FeignConfig
 * @description:
 * @author: Author
 * @create: 2024-02-25 19:15
 * @Version 1.0
 **/
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            return;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        requestTemplate.header(Cst.ACCESS_TOKEN, request.getHeader(Cst.ACCESS_TOKEN));
    }
}
