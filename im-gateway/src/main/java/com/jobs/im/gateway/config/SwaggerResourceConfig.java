package com.jobs.im.gateway.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Sets;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * @program: im
 * @ClassName: SwaggerResourceConfig
 * @description: 使用Spring Boot单体架构集成swagger时，是通过包路径进行业务分组，然后在前端进行不同模块的展示，而在微服务架构下，单个服务类似于原来业务组；
 *               springfox-swagger提供的分组接口是swagger-resource，返回的是分组接口名称、地址等信息； 在Spring
 *               Cloud微服务架构下，需要swagger-resource重写接口，由网关的注册中心动态发现所有的微服务文档
 * @author: Author
 * @create: 2024-02-22 14:34
 * @Version 1.0
 **/
@Configuration
public class SwaggerResourceConfig implements SwaggerResourcesProvider {
    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private GatewayProperties gatewayProperties;

    // 排除服务实例
    private Set<String> swaggerExcludes = Sets.newHashSet("im-eureka");

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();

        // 取出gateway的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        // 过滤
        gatewayProperties.getRoutes().stream()
            // 过滤同名服务
            .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
            // 过滤要排除的服务
            .filter(routeDefinition -> !swaggerExcludes.contains(routeDefinition.getId())).forEach(route -> {
                route.getPredicates().stream()
                    // 忽略配置文件中断言中配置的Path为空的配置项
                    .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                    // 将Path中的路由地址由**改为v2/api-docs，swagger就是通过这个地址来获取接口文档数据的，可以通过访问：ip:port/v2/api-docs来体会接口数据
                    .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(), predicateDefinition
                        .getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("**", "v2/api-docs"))));
            });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
