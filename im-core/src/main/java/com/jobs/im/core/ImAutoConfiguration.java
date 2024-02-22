package com.jobs.im.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: im
 * @ClassName: ImAutoConfiguration
 * @description:
 * @author: Author
 * @create: 2024-02-22 16:51
 * @Version 1.0
 **/
@ComponentScan
@Configuration
public class ImAutoConfiguration {
    @Bean
    void init() {}
}
