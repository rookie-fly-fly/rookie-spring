package com.dyg.rookie.spring.common.feignlog;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * FeignLogAspect 类是 feign调用日志切面 作用：记录feign调用的入参、时长、出参 相关概念： 通知：执行的目标方法
 * 切点：满足条件的目标方法 连接点：切点的全集 切面：切点和通知的组合
 *
 * @author rookie-spring
 * @date 2022/7/1 18:21
 * @copyright All rights reserved
 **/
@Component
@Aspect
@Slf4j
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

    /**
     * 使用feignClient注解的接口
     */
    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void pointAnnotation() {
        throw new UnsupportedOperationException();
    }

    /**
     * invoke 方法是 在切点做的操作：记录入参、feign调用耗时、出参
     * 通过@Around注解声明切点，项目中的feign包的所有方法均为切点,合称连接点
     *
     * @param thisJoinPoint thisJoinPoint
     * @return 原方法执行结果
     * @author rookie-spring
     * @date 2021/4/13 20:06
     */
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
            log.error("feign调用异常", exception);
        } finally {
            // 4.执行完成后打印执行结果,
            String outJson = getLimitLengthString(outParam);
            log.info(" 方法名:{} 入参：{} 耗时信息: {}ms 返回结果：{}", name, inputJson, System.currentTimeMillis() - start, outJson);
        }
        return outParam;
    }

}
