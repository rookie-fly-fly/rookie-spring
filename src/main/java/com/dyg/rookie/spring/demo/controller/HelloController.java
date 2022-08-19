package com.dyg.rookie.spring.demo.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.dyg.rookie.spring.common.exception.BusinessRuntimeException;
import com.dyg.rookie.spring.common.log.Log;
import com.dyg.rookie.spring.common.resp.CallResponse;
import com.dyg.rookie.spring.demo.domain.DemoVO;
import com.dyg.rookie.spring.demo.feign.domain.FeignQueryVO;
import com.dyg.rookie.spring.demo.feign.domain.FeignResponseVO;
import com.dyg.rookie.spring.demo.service.IHelloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ExecutorService;

/**
 * demo类
 *
 * @author rookie-spring
 * @date 2022/6/22 8:19
 */
@Tag(name = "项目示例类", description = "项目示例类-demo")
@Slf4j
@RestController
@RequestMapping("/demo")
public class HelloController {

    @Autowired
    private IHelloService helloService;

    @Autowired
    private ExecutorService commonThreadPool;

    /**
     * 测试Hello
     *
     * @return {@link String }
     * @author rookie-spring
     * @date 2022/7/2 14:35
     **/
    @Operation(summary = "测试Hello")
    @GetMapping("/hello")
    public String hello() {
        return "Welcome to reactive ~";
    }

    /**
     * 测试Hello1
     *
     * @param latency : 传参
     * @return {@link String }
     * @author rookie-spring
     * @date 2022/7/2 14:34
     **/
    @Operation(summary = "测试Hello1")
    @GetMapping("/hello1/{latency}")
    public String hello1(@PathVariable int latency) {
        return "Welcome to reactive ~" + latency;
    }

    /**
     * Log日志打印demo
     *
     * @param id : 请求参数id
     * @return {@link CallResponse <DemoVO> }
     * @author rookie-spring
     * @date 2022/7/2 14:32
     **/
    @Operation(summary = "@Log日志打印demo")
    @GetMapping("/hello_db/{id}")
    @Log("hello rookie-spring!")
    public CallResponse<DemoVO> helloDb(@PathVariable int id) {
        return CallResponse.success(helloService.hello(id));
    }

    /**
     * callResponseSuccessDemo 方法是 成功返回结果的例子
     *
     * @return 成功的CallResponse
     * @author rookie-spring
     * @date 2022/6/27 9:37
     */
    @Operation(summary = "成功返回结果的例子")
    @GetMapping("/success_demo")
    public CallResponse<String> callResponseSuccessDemo() {
        return CallResponse.success("成功示例");
    }

    /**
     * callResponseErrorDemo 方法是 失败返回结果的例子
     *
     * @return 失败的CallResponse
     * @author rookie-spring
     * @date 2022/6/27 9:37
     */
    @Operation(summary = "失败返回结果的例子")
    @GetMapping("/error_demo")
    public CallResponse<String> callResponseErrorDemo() {
        return CallResponse.error("失败示例");
    }

    /**
     * businessRuntimeExceptionDemo 方法是 业务异常的例子
     *
     * @param requestParam 请求参数
     * @return 返回结果
     * @author rookie-spring
     * @date 2022/6/27 9:38
     */
    @Operation(summary = "业务异常的例子")
    @GetMapping("/throw_business_exception_demo")
    public CallResponse<String> businessRuntimeExceptionDemo(String requestParam) {
        if (CharSequenceUtil.isEmpty(requestParam)) {
            // 抛出业务异常后,直接通过GlobalExceptionHandler处理
            throw new BusinessRuntimeException("业务异常示例");
        }
        return CallResponse.success(requestParam);
    }

    /**
     * validatorDemo 方法是 参数校验的例子
     *
     * @param demoVO 待校验参数
     * @return 结果
     * @author rookie-spring
     * @date 2022/6/28 9:06
     */
    @Operation(summary = "参数校验的例子")
    @PostMapping("/validator_demo")
    public CallResponse<String> validatorDemo(@RequestBody @Valid DemoVO demoVO) {
        log.info("requestParam:{}", JSON.toJSONString(demoVO));
        return CallResponse.success("参数校验示例");
    }

    /**
     * threadPoolDemo 方法是 公共线程池的例子
     *
     * @author rookie-spring
     * @date 2022/7/1 13:16
     */
    @Operation(summary = "公共线程池的例子")
    @GetMapping("/thread_pool_demo")
    public void threadPoolDemo() {
        commonThreadPool.execute(() -> log.info("threadPoolDemo"));
    }

    /**
     * asyncDemo 方法是 异步方法的示例
     *
     * @return 异步线程名称
     * @author rookie-spring
     * @date 2022/7/1 14:41
     */
    @Operation(summary = "异步方法的示例")
    @GetMapping("/async_demo")
    public CallResponse<String> asyncDemo() {
        helloService.asyncDemo();
        return CallResponse.success("转去异步执行");
    }

    /**
     * feignDemo 方法是 feign调用的示例，同时记录feign调用日志
     *
     * @param feignQueryVO feign请求对象
     * @return 调用结果
     * @author rookie-spring
     * @date 2022/7/1 14:52
     */
    @Operation(summary = "feign调用的示例，同时记录feign调用日志")
    @GetMapping("/feign_demo")
    public CallResponse<FeignResponseVO> feignDemo(FeignQueryVO feignQueryVO) {
        return CallResponse.success(helloService.feignDemo(feignQueryVO));
    }

}
