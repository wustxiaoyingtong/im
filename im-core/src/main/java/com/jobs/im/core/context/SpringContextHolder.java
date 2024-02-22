package com.jobs.im.core.context;

import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @program: im
 * @ClassName: SpringContextHolder
 * @description:
 * @author: Author
 * @create: 2024-02-22 16:38
 * @Version 1.0
 **/
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(String name) {
        Object obj = getApplicationContext().getBean(name);
        return Objects.nonNull(obj) ? (T)obj : null;
    }

    public static <T> T getBean(Class<T> clazz) {
        Object obj = getApplicationContext().getBean(clazz);
        return Objects.nonNull(obj) ? (T)obj : null;
    }
}
