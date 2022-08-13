package com.dyg.rookie.spring.common.log;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * LogAdvice 类是 记录日志的切面类 1.通过@Aspect 注解声明一个切面 2.通过@Component
 * 让此切面成为Spring容器管理的Bean
 *
 * @author rookie-spring
 * @date 2021-04-13 20:02
 **/
@Aspect
@Component
@Slf4j
public class LogAdvice {

    /**
     * invoke 方法是 在切点做的操作：记录当前方法耗时 通过@Around注解声明一个建言，并使用定义了自定义注解 @Log 的方法作为切入点
     *
     * @param thisJoinPoint thisJoinPoint
     * @param logger        log注解
     * @return 结果
     * @author rookie-spring
     * @date 2021/4/13 20:06
     */
    @Around("@annotation(logger)")
    public Object invoke(ProceedingJoinPoint thisJoinPoint, Log logger) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        // 1.获取注解上的属性，如果无就默认当前的方法名 methodSignature.getName()
        String name = StringUtils.isEmpty(logger.value()) ? methodSignature.getName() : logger.value();
        Object[] args = thisJoinPoint.getArgs();
        // 2.记录开始的时间戳
        long start = System.currentTimeMillis();
        Object outParam = null;
        try {
            // 3.执行切入点的方法体
            outParam = thisJoinPoint.proceed();
        } finally {
            // 4.输出日志
            log.info("方法：{} 入参:{} 耗时信息: {}ms 返回结果：{}", name, JSON.toJSONString(args),
                    System.currentTimeMillis() - start, JSON.toJSONString(outParam)
            );
        }
        return outParam;
    }

}
