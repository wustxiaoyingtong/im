package com.jobs.im.core.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobs.im.core.interceptor.WebInterceptor;

/**
 * @program: im
 * @ClassName: WebMvcConfig
 * @description:
 * @author: Author
 * @create: 2024-02-22 18:06
 * @Version 1.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer, ServletContextInitializer {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }

    /**
     * Description: 设置跨域访问 说明： addMapping：可以被跨域的路径，”/**”表示无限制。
     * allowedMethods：允许跨域访问资源服务器的请求方式，如：POST、GET、PUT、DELETE等，“*”表示无限制。
     * allowedOrigins：”*”允许所有的请求域名访问跨域资源，当设置具体URL时只有被设置的url可以跨域访问。
     * 例如：allowedOrigins(“https://www.baidu.com”),则只有https://www.baidu.com能访问跨域资源。
     *
     * @param registry
     * @return
     * @throws
     * @author Author
     * @date 2024/2/22 18:11
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowCredentials(true);
    }

    /**
     * Description: 配置路径访问忽略大小写
     *
     * @param configurer
     * @return
     * @throws
     * @author Author
     * @date 2024/2/22 18:12
     **/
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        log.info("server start up");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        WebMvcConfigurer.super.configureContentNegotiation(configurer);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**").excludePathPatterns("/favicon.ico",
            "/doc.html", "/webjars/**", "/v2/**", "/swagger-resources/**");
    }

    /**
     * Description: 定义转换器
     *
     * @param converters
     * @return
     * @throws
     * @author CatEyes
     * @date 2023/4/4 20:59
     **/
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        // 设置解析JSON工具类
        ObjectMapper objectMapper = new ObjectMapper();
        // 设置解析日期的工具类
        objectMapper.setDateFormat(GlobalJsonDateConvert.INSTANCE);
        // 忽略未知属性 防止解析报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonConverter.setObjectMapper(objectMapper);
        List list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        jsonConverter.setSupportedMediaTypes(list);
        return jsonConverter;
    }
}
