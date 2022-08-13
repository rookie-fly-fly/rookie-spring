# 跟随项目的说明文档

[![警报](https://sonar.bugboke.com/api/project_badges/measure?project=BASE20222806093142&metric=alert_status&token=squ_563768921ccdd7b4f6c6c51b9f7240756efcafe9)](https://sonar.bugboke.com/dashboard?id=BASE20222806093142)

## 目录说明

- doc 文档目录
- src 源码目录
    - base基础代码目录
    - common 公共代码目录
      - exception 异常处理
      - feignlog 全局feign日志记录
      - log 自定义日志注解
      - resp 项目返回值定义
      - threadpool 公共线程池
    - config 配置文件目录
    - demo 演示目录
      - controller 接口目录
      - domain 模块目录，domain和VO的目录
      - feign feign接口和对象目录
      - mapper dao接口层
      - service 服务类接口
        - impl 接口实现目录
      - constant 常量类
        - errorcode 错误码
- test 测试目录
- .gitignore git的忽略文件，

## 封装返回对象:CallResponse

项目中，在Controller层进行返回时，均需使用 `CallResponse` 进行封装。该类位与 `com.dyg.rookie.spring.common.resp`包下。

`CallResponse` 的结构如下：

- code:返回值编码，"000000"表示成功，其他表示失败。
- msg:返回值描述，失败时用来描述失败原因。
- result:返回值内容，成功时显示返回结果。result使用泛型，可以是任意类型。

使用规范：

- 仅在Controller层使用 `CallResponse`进行封装。
- 在Controller层使用 `CallResponse`进行封装时，必须使用 `CallResponse`的静态方法进行封装，可以根据期望调用合适的静态方法。
- 使用时需要指定result的类型。

通过静态方法提供了多个success和failure的，可以方便的返回结果。

- CallResponse.success(result)
- CallResponse.success(message,result)
- CallResponse.success(code,message,result)
- CallResponse.error(message)
- CallResponse.error(code,message)

以上静态方法详情参见CallResponse类。

使用示例如下：

成功时：

```java
public class Test {
    public CallResponse<String> callResponseSuccessDemo() {
        return CallResponse.success("成功示例");
    }
}
```

失败时：

```java
public class Test {
    public CallResponse<String> callResponseErrorDemo() {
        return CallResponse.error("失败示例");
    }
}
```

## 全局异常捕获

全局异常捕获用来捕获项目中所有的异常，并返回给前端。

本项目实现了全局异常捕获，是通过 `GlobalExceptionHandler`类实现的。同时，也定义了业务异常类 `BusinessRuntimeException`，用来捕获业务异常。

### 业务异常类

业务异常类为 `BusinessRuntimeException`，有 `code`和 `message`属性，分别用来描述异常编码和异常描述。异常编码和异常描述通过错误码进行自定义。

在业务逻辑中，出现不符合预期的情况时，直接抛出业务异常，可以自定义错误码和错误描述，抛出后直接阻断业务，进入全局异常捕获处理，返回给前端。

使用方式：

```java
public class Test {
    public CallResponse<String> businessRuntimeExceptionDemo(String message) {
        if (StrUtil.isEmpty(message)) {
            throw new BusinessRuntimeException("业务异常示例");
        }
        return CallResponse.success(message);
    }
}
```

### 全局异常处理类

全局异常处理类为 `GlobalExceptionHandler`，用来处理全局异常。在 `com.dyg.rookie.spring.common.exception`包下。

- 当前对业务异常进行单独处理，如果有其他异常需要单独捕获处理，可以通过 `@ExceptionHandler`注解进行捕获。
- 对Exception进行了兜底，如果没有对应的异常处理，会走到该兜底策略。
- 处理后均返回 `CallResponse`，code即错误码，message为异常的message(业务异常通常是自定义的错误描述，未显式捕获的异常走兜底策略时，均提示“系统异常，请联系管理员”)。

### 解决异常的性能问题

通过JMH进行性能问题验证，通过 `ExceptionBenchmark`和 `ExceptionOptimizationBenchmark`进行,结果如下

`ExceptionBenchmark`的结果如下：

```dos
Benchmark                                                 Mode  Cnt    Score    Error  Units
ExceptionBenchmark.createExceptionWithoutThrowingIt       avgt   10   14.443 ±  4.204  ms/op
ExceptionBenchmark.doNotThrowException                    avgt   10    0.056 ±  0.009  ms/op
ExceptionBenchmark.throwAndCatchException                 avgt   10   15.056 ±  3.378  ms/op
ExceptionBenchmark.throwExceptionAndUnwindStackTrace      avgt   10  189.837 ± 55.140  ms/op
ExceptionBenchmark.throwExceptionWithoutAddingStackTrace  avgt   10    0.402 ±  0.041  ms/op
```

| 效率  | 描述              | 结果                                      |
|-----|-----------------|-----------------------------------------|
| 1   | 不产生异常           | 0.056 ±(99.9%) 0.009 ms/op [Average]    |
| 2   | 抛出并捕获异常，但不追踪堆栈  | 0.402 ±(99.9%) 0.041 ms/op [Average]    |
| 3   | 创建异常，不抛出        | 14.443 ±(99.9%) 4.204 ms/op [Average]   |
| 4   | 抛出并捕获异常         | 15.056 ±(99.9%) 3.378 ms/op [Average]   |
| 5   | 抛出并捕获异常，并展开堆栈追踪 | 189.837 ±(99.9%) 55.140 ms/op [Average] |

`ExceptionOptimizationBenchmark`的结果如下：

```dos
Benchmark                                                             Mode  Cnt   Score   Error  Units
ExceptionOptimizationBenchmark.doNotThrowException                    avgt   10   0.064 ± 0.008  ms/op
ExceptionOptimizationBenchmark.throwAndCatchBusinessRuntimeException  avgt   10   0.261 ± 0.029  ms/op
ExceptionOptimizationBenchmark.throwAndCatchException                 avgt   10  15.297 ± 2.507  ms/op
```

`ExceptionOptimizationBenchmark`的结果证明，通过给`BusinessRuntimeException`类重写`fillInStackTrace`
方法来实现不追踪堆栈的异常，可以有效降低异常的性能损耗。在`ExceptionOptimizationBenchmark`
中把 `BusinessRuntimeException` 和 `Exception`进行了对比,有明显差异。

- 抛出不追踪堆栈的 `BusinessRuntimeException` 类,耗时是不抛出异常的4倍左右
- 抛出追踪堆栈的 `Exception` 类,耗时是不抛出异常的239倍左右

将 `BusinessRuntimeException` 类重写的 `fillInStackTrace` 方法注释掉后,再次跑一次性能测试,结果如下：

```dos
Benchmark                                                             Mode  Cnt   Score   Error  Units
ExceptionOptimizationBenchmark.doNotThrowException                    avgt   10   0.072 ± 0.036  ms/op
ExceptionOptimizationBenchmark.throwAndCatchBusinessRuntimeException  avgt   10  22.925 ± 5.429  ms/op
ExceptionOptimizationBenchmark.throwAndCatchException                 avgt   10  18.004 ± 5.610  ms/op
```

耗时大幅提升,从0.261 ± 0.029->22.925 ± 5.429,耗时增加了87倍左右。

相关验证文章：[https://www.baeldung.com/java-exceptions-performance](https://www.baeldung.com/java-exceptions-performance)

性能测试框架-Benchmark相关内容可参考附录4。

## 校验框架

检验框架采用 Hibernate validator 进行参数校验。版本为6.2.3。

```xml
<!-- 集成validation框架做参数校验 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

官方网站:https://hibernate.org/validator/documentation/

示例:`HelloController` 类的 `validatorDemo` 方法。可使用如下参数改动某项为不符合后进行校验效果查看。

```json
{
  "name": "String",
  "age": 1,
  "email": "123@qq.com",
  "list": [
    "1"
  ],
  "nestedVO": {
    "name": "String"
  }
}
```

通过全局异常捕获的方式，将校验结果进行拦截，处理后构建返回内容。

基础的校验方式可查看附录1。

## JSON框架

JSON操作框架采用 fastjson2。

```xml
<!-- fastjson2 操作JSON数据 -->
<dependency>
    <groupId>com.alibaba.fastjson2</groupId>
    <artifactId>fastjson2-extension</artifactId>
    <version>2.0.8</version>
</dependency>
```

文档见:https://alibaba.github.io/fastjson2/

Spring的集成方案:https://alibaba.github.io/fastjson2/spring_support_cn

## sonar


## Spring Boot 及 Spring Cloud 版本

项目使用的 Spring Boot 及 Spring Cloud 版本如下：

```text
Spring Boot：2.7.0
Spring Cloud：2021.0.3
```

Spring Boot 依赖通过 `<parent>` 引入,`Spring Boot 2.7.x` 初始发布时间 2022-05-19，开源支持截止时间 2023-11-18，商业支持截止时间 2025-02-18。

```xml

<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.0</version>
</parent>
```

Spring Cloud 依赖通过 `dependencyManagement` 控制 Cloud 相关依赖的版本。

```xml
<!-- 控制全局的 Spring Cloud 版本 -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2021.0.3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

使用 `dependencyManagement` 控制全局的 Spring Cloud 版本后，引入类似 openFeign、config 等组件时均不需指定 `version`,只需要`<groupId>`和`<artifactId>`
即可，如：

```xml
 <!-- openFeign 的相关依赖 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

如需使用 Spring Cloud 组件的其他版本，只需要在自己的模块dependencies中声明一个版本号。

与dependencies区别：

1. Dependencies相对于dependencyManagement，所有生命在dependencies里的依赖都会自动引入，并默认被所有的子项目继承。
2. dependencyManagement里只是声明依赖，并不自动实现引入，因此子项目需要显示的声明需要用的依赖。如果不在子项目中声明依赖，是不会从父项目中继承下来的；只有在子项目中写了该依赖项，并且没有指定具体版本，才会从父项目中继承该项，并且version和scope都读取自父pom;另外如果子项目中指定了版本号，那么会使用子项目中指定的jar版本。

相关网址：

- Spring Boot:https://spring.io/projects/spring-boot
- Spring Cloud:https://spring.io/projects/spring-cloud
  两者的版本对应关系参考附录2。

## swagger3集成

本项目集成了 Swagger3 文档生成工具。使用的是开源项目 `knife4j`集成的版本，对应依赖如下：

```xml
<!-- 开源项目 knife4j，集成 swagger3，接口文档工具 -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```

对应的配置类为 `Swagger3Config`。

启动 swagger3 的注解为 `@EnableOpenApi`。

访问地址

- `knife4j`提供的地址:`http://localhost:8080/doc.html`
- `swagger`原生访问地址：`http://localhost:8080/swagger-ui/index.html`

`knife4j`除了界面美化之外，还做了一些增强，暂时没看到特别有效的功能，后续再看。

由于Spring Boot 版本过高，会出现 `Failed to start bean 'documentationPluginsBootstrapper'`的异常。
需要有以下两个操作：

1. 添加了 `springfoxHandlerProviderBeanPostProcessor`方法。
2. 添加配置项，内容如下：

```properties
#swagger整合异常处理
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
```

`swagger`的注解应用实例请参考 `com.dyg.rookie.spring.demo.controller`下HelloController类的 `validatorDemo`方法。对方法、涉及的类及属性均进行注释，用以生成文档。

`swagger3`支持了业界真正的 api 文档标准 `OpenApi`(其是由 Swagger 来维护的，并被linux列为api标准，从而成为行业标准)，因此项目中使用 `swagger3`的注解，`swagger3`
的包名为 `io.swagger.core.v3`，swagger2的包名为 `io.swagger`，引入时可以看下，比如 `@Tag`在两个包中都有，不要引错了。

| swagger2           | OpenAPI 3                                                       | 注解位置                         |
|--------------------|-----------------------------------------------------------------|------------------------------|
| @Api               | @Tag(name = “接口类名”,description = “接口类描述”)                       | Controller 类上                |
| @ApiOperation      | @Operation(summary =“接口方法描述”)                                   | Controller 方法上               |
| @ApiImplicitParams | @Parameters                                                     | Controller 方法上               |
| @ApiImplicitParam  | @Parameter(description=“参数描述”)                                  | Controller 方法上 @Parameters 里 |
| @ApiParam          | @Parameter(description=“参数描述”)                                  | Controller 方法的参数上            |
| @ApiIgnore         | @Parameter(hidden = true) 或 @Operation(hidden = true) 或 @Hidden | -                            |
| @ApiModel          | @Schema(description = "类描述")                                    | DTO类上                        |
| @ApiModelProperty  | @Schema(description = "属性描述")                                   | DTO属性上                       |

## Spring Boot Admin 客户端

本项目使用了 Spring Boot Admin 客户端，可以在管理端查看项目的状态。依赖Spring actuator 暴露查询项目信息的接口。

依赖信息如下

```xml
<!-- actuator 提供 Spring Boot Admin 调用的相关接口用于监控 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
    <exclusions>
        <!-- 切换log4j2日志读取 -->
        <exclusion>
            <artifactId>logback-core</artifactId>
            <groupId>ch.qos.logback</groupId>
        </exclusion>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

配置文件信息如下：

```properties
#Actuator 暴露所有端点
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
#默认所有的监控点路径都在/actuator/*
management.endpoints.web.base-path=/actuator
```

可通过 `http://localhost:9201/actuator/beans` 查看是否生效。

## 公共线程池配置: `commonThreadPool`

通过 `ThreadPoolConfig` 来声明项目中的线程池，默认配置了公共线程池 `commonThreadPool` 。通常在需要另起线程处理某些事情时进行使用,例如在统计场景下，多线程查询后进行数据聚合以加快响应速度。

公共线程池的线程的名称是 `common-t{线程编号}`。

使用示例:

```java
public class HelloController {
    @Autowired
    private ExecutorService commonThreadPool;

    public void threadPoolDemo() {
        commonThreadPool.execute(() -> {
            log.info("threadPoolDemo");
        });
    }
}
```

使用示例可参考 `HelloController`中`threadPoolDemo`方法。

如有需要可以另外声明线程池，各参数可以按需调整。声明方式如下：

```java
public class ThreadPoolConfig {
    @Bean("线程池名称")
    public ExecutorService buildThreadPool() {
        String prefix = "线程名称前缀";
        return ThreadPoolBuilder.build(prefix, 核心线程数, 最大线程数, 队列长度);
    }
}
```

线程池的创建是通过自定义线程池工厂 `CustomThreadFactory` 和线程池构造器 `ThreadPoolBuilder` 来实现的。可以通过修改 prefix 来自定义线程池中的线程名称。

## 默认异步执行器:asyncTaskExecutor

通过 `AsyncConfig` 指定默认的公共异步执行器，公共线程池 `commonThreadPool`。通常用来做耗时较长或对返回无影响的异步任务，例如业务执行后发送邮件通知等。

使用时直接在方法上加上 `@Async` 注解即可。不需要显式的声明执行器。

使用示例：

```java
public class Test {

    @Async
    public void asyncDemo() {
        log.info("当前执行线程来自默认异步任务执行器，{}", Thread.currentThread()
                .getName());
    }
}
```

参考 `HelloServiceImpl` 中的 `asyncDemo` 方法。

## 错误码

本项目为spring示例服务，对应的前两位为 AA，详细规则见错误码文件的*编码规则*页。错误码示例参照 `DemoErrorCodeEnum`,样例如下：

```
    /**
     * AA-0-A-[01,99] spring示例模块-参数错误-示例业务 错误码
     * AA:spring示例服务（前两位区分服务，本项目是spring示例服务，前两位为AA）
     * 0:错误类型（第三位标识错误类型，0：参数错误、1：逻辑错误:2：调用错误:3：解析错误、4：系统异常）
     * A:模块代号（第四位表示服务中的模块或功能，A:模块A、B:模块B、C:模块C，模块代码按需添加）
     * [01,99]:错误码顺序（后两位）
     */
    DEMO_ERROR_CODE("AA0A01","错误码示例");
```

项目中各模块的错误码，后续按需添加，以错误码文件中的内容为准：

- A:模块A
- B:模块B
- C:模块C

## 集成 `openFeign`

引入 openfeign 依赖，用来处理外部调用。版本是 3.1.3。

```xml
<!-- openFeign 的相关依赖 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

将feign理解为和Mapper同级的数据持久层，只是形式由：mybatis连接数据库->feign调用外部服务。

feign分为提供公共功能和提供个性化功能。

1. 提供公共功能的相关规则如下：

- 提供公共功能的部分(比如基础数据服务提供的字典值等相关功能)，可以新建和业务同级的`package` 进行管理。
    - feign接口放在 `feign` 包下。
    - feign相关对象放在 `domain` 包下。
    - feign相关的处理类放在 `service` 包下。
- `feign` 接口的命名形式是 `XXXXFeign`，比如`DictFeign`调用外部服务的字典接口。
- 对`feign`接口均需创建处理类，`feign`接口不能直接被调用，只能是处理类被调用。 

2. 提供个性化功能的相关规则如下：

- 每个模块可以单独管理自己的个性化功能使用的`feign`调用（比如订单模块，可能需要去获取订单流程相关的信息，由外部服务进行提供，，本项目内其他模块不需使用），在这种情况下，各模块可能存在调用同一外部服务的情况。
- 在自己模块的目录下创建 `feign` 包，创建feign接口、对象和处理类，参考包`com.dyg.rookie.spring.demo.feign`。
    - feign接口放在根目录下
    - 对象放在domain包下
    - 处理类放在本模块的service下。
- 创建的`feign`接口，命名形式是`XXXXFeign.java`，方便后期对 feign 统一管理，参考 `DemoFeign`。
- 对feign接口均需创建处理类，feign接口不能直接被调用，只能是处理类被调用。 处理类参考`DemoFeignServiceImpl`。

## `@Log` 实现日志输出

引入自定义注解 `@Log`，用来实现日志输出。实现在包 `com.dyg.rookie.spring.common.log` 中，实现方案是：

1. 自定义注解 `@Log`。
2. 声明切面类 `LogAspect`。拦截被 `@Log` 注解的方法。进行入参、出参、耗时的日志打印。格式为`"方法：{} 入参:{} 耗时信息: {}ms 返回结果：{}"`
3. 在希望记录日志的方法上加上 `@Log` 注解。如果需要自定义日志中方法后的内容，可以使用 `@Log("方法信息")`。若仅使用 `@Log`则方法信息为方法名。

使用样例如下：

```java
private class HelloController {

    @Log("hello rookie-spring!")
    public CallResponse<DemoVO> helloDb(@PathVariable int id) {
        return CallResponse.success(helloService.hello(id));
    }
}
```

具体可参考 `HelloController` 的 `helloDb` 方法。输出格式如下：

```text
2022-07-02 14:49:52 680 [INFO] [c=LogAdvice] [51] [http-nio-9201-exec-1] [TID: N/A] 方法：hello rookie-spring! 入参:[0] 耗时信息: 380ms 返回结果：{"code":"000000","message":"成功"}

```

## `FeignLogAspect` 实现 `feign` 日志记录

通过 `FeignLogAspect` 对本项目所有的 `feign` 接口进行日志记录。保证记录 `feign` 调用的出入参、耗时信息。

`FeignLogAspect` 以 `@FeignClient`注解作为切点，拦截 `feign` 接口的调用。实现方案如下：

1. 通过 `@PointCut` 定义切点表达式，通过 `@within` 实现切点表达式含义：含 `@FeignClinet` 注解的类的所有方法（动态生成的 `feign`代理类将继承 `@FeignClient` 注解）。

```java
public class FeignLogAspect {

    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void pointAnnotation() {

    }
}
```

2. 在 `invoke` 方法上用 `@Around` 声明切点

```java
public class FeignLogAspect {
    @Around("pointAnnotation()")
    public Object invoke(ProceedingJoinPoint thisJoinPoint) throws Throwable {

    }
}
```

3.在 `invoke` 方法中实现日志记录

```java
public class FeignLogAspect {
    @Around("pointAnnotation()")
    public Object invoke(ProceedingJoinPoint thisJoinPoint) throws Throwable {

        // 1.获取当前的方法名
        String name = thisJoinPoint.getSignature().getName();
        Object[] inputParam = thisJoinPoint.getArgs();
        // 2.打印入参
        String inputJson = getLimitLengthString(inputParam);

        long start = System.currentTimeMillis();
        Object outParam = null;
        try {
            // 3.执行切入点的方法体
            outParam = thisJoinPoint.proceed();
        } catch (Exception exception) {
            log.error("feign调用异常");
            throw exception;
        } finally {
            // 4.执行完成后打印执行结果,
            String outJson = getLimitLengthString(outParam);
            log.info(" 方法名:{} 入参：{} 耗时信息: {}ms 返回结果：{}",
                    name, inputJson, System.currentTimeMillis() - start, outJson);
        }
        return outParam;
    }
}
```

4. 在处理出参、入参时，通过方法进行了截取，防止超长日志的输出。当前指定的长度为 `300`，日志长度超过的部分直接不做输出。

```java
public class FeignLogAspect {
    /**
     * 出参、入参的限制长度,输出时仅截取该长度
     */
    private static final int LEN_LIMIT = 300;

    /**
     * getLimitLengthString 方法是 根据传入的对象获取到限制长度的字符串
     *
     * @param object 传入对象
     * @return 限长字符串
     * @author rookie-spring
     * @date 2022/4/14 8:24
     */
    private static String getLimitLengthString(Object object) {

        String json = JSON.toJSONString(object);
        if (json.length() > LEN_LIMIT) {
            json = JSON.toJSONString(object).substring(0, LEN_LIMIT);
        }
        return json;
    }
}

```

使用示例可以参考 `HelloController` 的 `feignDemo` 方法。输出格式如下：

```text
2022-07-02 15:21:08 824 [INFO] [c=FeignLogAspect] [101] [http-nio-9201-exec-3] [TID: N/A]  方法名:getDictValueListByDictTypeCode 入参：["repair_progress_query"] 耗时信息: 521ms 返回结果：{"code":"00","message":"操作成功","result":[{"parentId":0,"typeCode":"repair_progress_query","valueCode":"5","valueDesc":"计算预计完成时间时，将要求完成时间+此天数得到。","valueId":4652,"valueName":"缓冲天数"},{"parentId":0,"typeCode":"repair_progress_query","valueCode":"400-618-1368","valueDesc":"咨询返修进度的联系电话","valueId":4653,"val
```

## 集成Mybatis

### 报错：PageHelper：com.github.pagehelper.PageHelper cannot be cast to org.apache.ibatis.plugin.Interceptor

因为 `PageHelper` 版本升级， 拦截器的插件出现变更，因此mybatis-config.xml中的拦截器配置也需要变更。

新版本的PagHelper，使用的是 PageInterceptor，不是之前的PageHelper，从文档上看，似乎连dialect也不用配置。
相关参考：https://blog.csdn.net/tiankong_12345/article/details/90269787

```xml

<plugins>
    <plugin interceptor="com.github.pagehelper.PageInterceptor">
        <!--            <property name="dialect" value="mysql"/>-->
        <property name="offsetAsPageNum"
                  value="true"/>
        <property name="rowBoundsWithCount"
                  value="true"/>
        <property name="pageSizeZero" value="true"/>
        <property name="reasonable"
                  value="true"/>
    </plugin>
</plugins>
```
