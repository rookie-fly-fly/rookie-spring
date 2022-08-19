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

    //@Bean
    //public Docket desertsApi() {
    //    // OAS:OpenAPI Specification
    //    return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).select()
    //            // RequestHandlerSelectors配置如何扫描接口
    //            .apis(RequestHandlerSelectors.basePackage("com.dyg.rookie.spring"))
    //            // PathSelectors配置哪些路径和请求方式需要生成API文档
    //            .paths(PathSelectors.any()).build().groupName("rookie-spring 接口文档V1.0").enable(true);
    //}
    //
    //private ApiInfo apiInfo() {
    //    return new ApiInfoBuilder().title("rookie-spring API").description("rookie-spring 接口文档")
    //            .contact(new Contact("rookie-spring", "", "believerA@aliyun.com")).version("1.0")
    //            .build();
    //}

    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.dyg.rookie.spring"};
        return GroupedOpenApi.builder().group("rookie-spring 接口文档V1.0")
                .pathsToMatch(paths)
                .addOperationCustomizer((operation, handlerMethod) -> {
                    return operation.addParametersItem(new HeaderParameter().name("groupCode").example("测试")
                            .description("集团code")
                            .schema(new StringSchema()._default("BR").name("groupCode").description("集团code")));
                })
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("rookie-spring 接口文档")
                        .version("1.0")
                        .description("rookie-spring 接口文档内容")
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")));
    }
}
