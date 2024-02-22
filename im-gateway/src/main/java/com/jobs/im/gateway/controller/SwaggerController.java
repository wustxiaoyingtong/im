package com.jobs.im.gateway.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

/**
 * @program: im
 * @ClassName: SwaggerController
 * @description:
 * @author: Author
 * @create: 2024-02-22 14:52
 * @Version 1.0
 **/
@RestController
public class SwaggerController {
    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;

    @Autowired(required = false)
    private UiConfiguration uiConfiguration;

    @Autowired
    private SwaggerResourcesProvider swaggerResourcesProvider;

    @GetMapping("/swagger-resources/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<>(
            Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()),
            HttpStatus.OK));
    }

    @GetMapping("/swagger-resources/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(new ResponseEntity<>(
            Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping("/swagger-resources")
    public Mono<ResponseEntity> swaggerResources() {
        return Mono.just(new ResponseEntity<>(swaggerResourcesProvider.get(), HttpStatus.OK));
    }
}
