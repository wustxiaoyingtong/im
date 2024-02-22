package com.jobs.im.core.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.jobs.im.core.context.SpringContextHolder;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: im
 * @ClassName: SwaggerConfig
 * @description:
 * @author: Author
 * @create: 2024-02-22 16:32
 * @Version 1.0
 **/
@EnableKnife4j
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.application.name}")
    private String application;

    /**
     * Description: 创建API应用 apiInfo() 增加API相关信息 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @param
     * @return Docket
     * @throws
     * @author Author
     * @date 2024/2/22 16:40
     **/
    @Bean
    public Docket createRestApi() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(
            new ResponseMessageBuilder().code(200).message("请求成功").responseModel(new ModelRef("ApiError")).build());
        responseMessages.add(
            new ResponseMessageBuilder().code(404).message("找不到资源").responseModel(new ModelRef("ApiError")).build());
        responseMessages.add(
            new ResponseMessageBuilder().code(400).message("参数错误").responseModel(new ModelRef("ApiError")).build());
        responseMessages
            .add(new ResponseMessageBuilder().code(401).message("未登录").responseModel(new ModelRef("ApiError")).build());
        responseMessages.add(
            new ResponseMessageBuilder().code(403).message("权限不足").responseModel(new ModelRef("ApiError")).build());
        responseMessages.add(
            new ResponseMessageBuilder().code(404).message("未找到资源").responseModel(new ModelRef("ApiError")).build());
        responseMessages.add(
            new ResponseMessageBuilder().code(405).message("请求方法不对").responseModel(new ModelRef("ApiError")).build());
        responseMessages.add(
            new ResponseMessageBuilder().code(500).message("服务器内部错误").responseModel(new ModelRef("ApiError")).build());
        return new Docket(DocumentationType.SWAGGER_2).globalResponseMessage(RequestMethod.GET, responseMessages)
            .globalResponseMessage(RequestMethod.POST, responseMessages)
            .globalResponseMessage(RequestMethod.PUT, responseMessages)
            .globalResponseMessage(RequestMethod.DELETE, responseMessages).apiInfo(apiInfo()).select()
            .apis(RequestHandlerSelectors.basePackage(basePackage())).paths(PathSelectors.any()).build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中） 访问地址：http://项目实际地址/doc.html
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(application)
            .contact(new Contact("xxx", "https://www.jobs.com/", "xxx@xx.com")).version("1.0.0").build();
    }

    /**
     * Description: 扫描包名
     *
     * @param
     * @return String
     * @throws
     * @author Author
     * @date 2024/2/22 16:39
     **/
    private String basePackage() {
        Map<String, Object> beans =
            SpringContextHolder.getApplicationContext().getBeansWithAnnotation(SpringBootApplication.class);
        if (CollectionUtils.isEmpty(beans)) {
            log.error("未正确扫描到启动类，程序退出");
            System.exit(1);
        }
        String className = beans.values().toArray()[0].getClass().getName();
        String packageName = className.substring(0, className.lastIndexOf(".")) + ".controller";
        log.info("Swagger basePackage is {}", packageName);
        return packageName;
    }
}
