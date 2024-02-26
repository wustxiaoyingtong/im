package com.jobs.im.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jobs.im.feign.client.RbacApiClient;

import lombok.Getter;

/**
 * @program: im
 * @ClassName: FeignBeanAgent
 * @description:
 * @author: Author
 * @create: 2024-02-25 16:47
 * @Version 1.0
 **/
@Component
public class FeignBeanAgent {
    @Autowired
    @Getter
    private RbacApiClient rbacApiClient;
}
