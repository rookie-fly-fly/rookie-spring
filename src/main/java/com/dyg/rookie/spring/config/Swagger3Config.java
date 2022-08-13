package com.dyg.rookie.spring.config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger3的配置类
 *
 * @author rookie-spring
 * @module config
 * @date 2022/6/28 16:11
 * @copyright All rights reserved
 */
@EnableOpenApi
@Configuration
public class Swagger3Config {
    /**
     * 解决springboot2.7.0 和springfox不兼容问题 Failed to start bean ‘
     * documentationPluginsBootstrapper ‘ ; nested exception…
     *
     * @return BeanPostProcessor
     */
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new Swagger3BeanPostProcessorVO();
    }

    @Bean
    public Docket desertsApi() {
        // OAS:OpenAPI Specification
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).select()
                // RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.dyg.rookie.spring"))
                // PathSelectors配置哪些路径和请求方式需要生成API文档
                .paths(PathSelectors.any()).build().groupName("rookie-spring 接口文档V1.0").enable(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("rookie-spring API").description("rookie-spring 接口文档")
                .contact(new Contact("rookie-spring", "", "believerA@aliyun.com")).version("1.0")
                .build();
    }
}
