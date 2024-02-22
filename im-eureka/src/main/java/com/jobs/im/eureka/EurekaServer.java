package com.jobs.im.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @program: im
 * @ClassName: EurekaServer
 * @description:
 * @author: Author
 * @create: 2024-02-22 10:38
 * @Version 1.0
 **/
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class, args);
    }
}
