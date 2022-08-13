# 附录3：Swagger

> 项目中使用第三方开源组件`Knife4j`作为接口文档服务，采用的是`Swagger3`版本。
## swagger 3 的使用
Swagger2（基于openApi3）已经在17年停止维护了，取而代之的是 sagger3（基于openApi3），而国内几乎没有 sagger3使用的文档，百度搜出来的都是swagger2的使用，这篇文章将介绍如何在 java 中使用 openApi3（swagger3）。

## 相关介绍
### Open API
OpenApi是业界真正的 api 文档标准，其是由 Swagger 来维护的，并被linux列为api标准，从而成为行业标准。

### Swagger
swagger 是一个 api 文档维护组织，后来成为了 Open API 标准的主要定义者，现在最新的版本为17年发布的 Swagger3（Open Api3）。

国内绝大部分人还在用过时的swagger2（17年停止维护并更名为swagger3）

swagger2的包名为 io.swagger，而swagger3的包名为 io.swagger.core.v3。

### SpringFox
SpringFox是 spring 社区维护的一个项目（非官方），帮助使用者将 swagger2 集成到 Spring 中。 常常用于 Spring 中帮助开发者生成文档，并可以轻松的在spring boot中使用。截至2020年4月，都未支持 OpenAPI3 标准。

> 补充：2020.7.14 发布了 3.0 支持 OpenAPI3，github 发布记录 但官网对 3.0 版本相关文档未完善，还是只有 swagger 2.0 相关的。
[升级到 OpenAPI3（java 中 swagger1.x 对应 OpenAPI2、swagger 2.x对应OpenAPI3）官方文档](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations#quick-annotation-overview)

3.0 相关特性
- 支持 Spring 5，Webflux（仅请求映射支持，尚不支持功能端点）、Spring Integration
- 补充官方在 spring boot 的自动装配 pringfox-boot-starter 以后可以直接依赖一个 dependency
- 与2.0更好的规范兼容性
- 支持OpenApi 3.0.3
- 轻依赖 spring-plugin，swagger-core
- 现有的swagger2批注将继续有效并丰富开放式API 3.0规范

### SpringDoc
SpringDoc也是 spring 社区维护的一个项目（非官方），帮助使用者将 swagger3 集成到 Spring 中。 也是用来在 Spring 中帮助开发者生成文档，并可以轻松的在spring boot中使用。

该组织下的项目支持swagger页面Oauth2登录（Open API3的内容），相较 SpringFox来说，它的支撑时间更长，无疑是更好的选择。但由于国内发展较慢，在国内不容易看到太多有用的文档，不过可以访问它的官网。它的使用了 swagger3（OpenAPI3），但 swagger3 并未对 swagger2 的注解做兼容，不易迁移，也因此，名气并不如 spring fox。

### swagger2和3的注解对应关系
| swagger2           | OpenAPI 3                                                       | 注解位置                         |
|--------------------|-----------------------------------------------------------------|------------------------------|
| @Api               | @Tag(name = “接口类名”,description = “接口类描述”)                       | Controller 类上                |
| @ApiOperation      | @Operation(summary =“接口方法描述”)                                   | Controller 方法上               |
| @ApiImplicitParams | @Parameters                                                     | Controller 方法上               |
| @ApiImplicitParam  | @Parameter(description=“参数描述”)                                  | Controller 方法上 @Parameters 里 |
| @ApiParam          | @Parameter(description=“参数描述”)                                  | Controller 方法的参数上            |
| @ApiIgnore         | @Parameter(hidden = true) 或 @Operation(hidden = true) 或 @Hidden | -                            |
| @ApiModel          | @Schema                                                         | DTO类上                        |
| @ApiModelProperty  | @Schema                                                         | DTO属性上                       |


## 参考文章
- [knife4j官方文档](https://doc.xiaominfo.com/knife4j)
- [SpringBoot集成swagger](https://blog.csdn.net/lsqingfeng/article/details/123678701)
- [Springboot 2.6.* + Swagger3](https://blog.csdn.net/yao22yao/article/details/125207679)
- [springboot集成swagger之knife4j实战](https://www.cnblogs.com/dxiaodang/p/14603610.html)
- [Swagger3 注解使用（Open API 3）](https://blog.csdn.net/qq_35425070/article/details/105347336)
- [OpenAPI 规范 (中文版)](https://openapi.apifox.cn/)
