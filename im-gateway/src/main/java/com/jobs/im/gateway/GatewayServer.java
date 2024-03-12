package com.jobs.im.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: im
 * @ClassName: GatewayServer
 * @description:
 * @author: Author
 * @create: 2024-02-22 10:58
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = {"com.jobs.im.gateway"})
@EnableDiscoveryClient
public class GatewayServer {
    private static Logger log = LoggerFactory.getLogger(GatewayServer.class);

    public static void main(String[] args) {
        SpringApplication.run(GatewayServer.class, args);
        log.info("*********************GatewayServer start!*********************");
    }
}
