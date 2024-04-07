package com.jobs.im.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: im
 * @ClassName: FileServer
 * @description:
 * @author: Author
 * @create: 2024-02-26 21:11
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = {"com.jobs.im.file", "com.jobs.im.feign"})
@EnableFeignClients(basePackages = "com.jobs.im.feign")
public class FileServer {
    private static Logger log = LoggerFactory.getLogger(FileServer.class);

    public static void main(String[] args) {
        SpringApplication.run(FileServer.class, args);
        log.info("*********************FileServer start!*********************");
    }
}
