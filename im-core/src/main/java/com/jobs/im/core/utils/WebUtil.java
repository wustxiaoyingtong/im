package com.jobs.im.core.utils;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jobs.im.core.common.Cst;

/**
 * @program: im
 * @ClassName: WebUtil
 * @description:
 * @author: Author
 * @create: 2024-02-23 15:52
 * @Version 1.0
 **/
public final class WebUtil {
    /**
     * Description: 请求头Header中获取Access-Token，兼容Get请求
     *
     * @param
     * @return String
     * @throws
     * @author Author
     * @date 2024/2/23 17:51
     **/
    public static String getAccessToken() {
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        String accessToken = httpServletRequest.getHeader(Cst.ACCESS_TOKEN);
        return StringUtils.isNotEmpty(accessToken) ? accessToken : httpServletRequest.getParameter(Cst.ACCESS_TOKEN);
    }

    /**
     * Description: 输出固定内容到返回
     *
     * @param response
     * @param data
     * @return
     * @throws
     * @author Author
     * @date 2024/2/23 15:55
     **/
    public static void renderJson(HttpServletResponse response, Object data) {
        // 设置编码格式
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object paramT, JsonGenerator paramJsonGenerator,
                SerializerProvider paramSerializerProvider) throws IOException {
                // 设置返回null转为 空字符串""
                paramJsonGenerator.writeString("");
            }
        });
        try {
            ServletOutputStream os = response.getOutputStream();
            os.write(objectMapper.writeValueAsString(data).getBytes("utf-8"));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
