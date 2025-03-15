package com.example.antmall.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class Knife4jConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("all")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.antmall"))
                .paths(PathSelectors.any())
                .build()
                // 关键：配置安全策略，让文档支持“Authorize”按钮
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    /**
     * 基本的文档信息配置
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("antmall-test")
                .description("我的描述")
                .contact(new Contact("daoren", "https://xxx.com", "xx@qq.com"))
                .version("1.0")
                .build();
    }

    /**
     * 配置全局Header: Authorization
     */
    private ApiKey apiKey() {
        // 参数1(name)：安全策略的名字（随意，但要和下面的 SecurityReference 一致）
        // 参数2(keyName)：实际请求头的字段名（常见 "Authorization"）
        // 参数3(in)：放在header
        return new ApiKey("Authorization", "Authorization", "header");
    }

    /**
     * 配置安全上下文
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    /**
     * 默认的全局权限配置
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[]{ authorizationScope };
        // 这里的 "Authorization" 要和上面的 apiKey() 里的 name 保持一致
        return Collections.singletonList(new SecurityReference("Authorization", scopes));
    }
}
