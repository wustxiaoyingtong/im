package com.jobs.im.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: im
 * @ClassName: ChatServer
 * @description:
 * @author: Author
 * @create: 2024-02-26 21:11
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = {"com.jobs.im.chat", "com.jobs.im.feign"})
@EnableFeignClients(basePackages = "com.jobs.im.feign")
public class ChatServer {
    private static Logger log = LoggerFactory.getLogger(ChatServer.class);

    public static void main(String[] args) {
        SpringApplication.run(ChatServer.class, args);
        log.info("*********************ChatServer start!*********************");
    }
}
