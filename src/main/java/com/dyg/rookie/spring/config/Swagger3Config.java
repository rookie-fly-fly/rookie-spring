package com.dyg.rookie.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger3的配置类
 *
 * @author rookie-spring
 * @module config
 * @date 2022/6/28 16:11
 * @copyright All rights reserved
 */
@Configuration
public class Swagger3Config {

    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.dyg.rookie.spring"};
        return GroupedOpenApi.builder().group("rookie-spring 接口文档V1.0")
                .pathsToMatch(paths)
                .addOperationCustomizer((operation, handlerMethod) -> operation
                        .addParametersItem(new HeaderParameter().name("SNBC").example("测试")
                                .description("SNBC").
                                schema(new StringSchema()._default("BR").name("groupCode").description("SNBC"))))
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("rookie-spring 接口文档")
                        .version("1.0")
                        .description("rookie-spring 接口文档内容")
                        .termsOfService("https://github.com/rookie-fly-fly/rookie-spring.git")
                        .license(new License().name("Apache 2.0")
                                .url("https://github.com/rookie-fly-fly/rookie-spring.git")));
    }
}
