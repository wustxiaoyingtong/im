package com.jobs.im.rbac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: im
 * @ClassName: RbacServer
 * @description:
 * @author: Author
 * @create: 2024-02-22 13:56
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = {"com.jobs.im.rbac", "com.jobs.im.feign"})
@EnableFeignClients(basePackages = "com.jobs.im.feign")
public class RbacServer {
    private static Logger log = LoggerFactory.getLogger(RbacServer.class);

    public static void main(String[] args) {
        SpringApplication.run(RbacServer.class, args);
        log.info("*********************RbacServer start!*********************");
    }
}
