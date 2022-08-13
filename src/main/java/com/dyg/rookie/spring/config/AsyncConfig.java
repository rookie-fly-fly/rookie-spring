package com.dyg.rookie.spring.config;

import com.dyg.rookie.spring.common.constants.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步配置类
 *
 * @author rookie-spring
 * @module config
 * @date 2022/7/1 13:36
 * @copyright All rights reserved
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 默认核心线程数 10
     */
    private static final int DEFAULT_CORE_POOL_SIZE = Constants.TEN_INT;
    /**
     * 默认最大线程数 10
     */
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = Constants.TEN_INT;
    /**
     * 默认工作队列长度 100
     */
    private static final int DEFAULT_CAPACITY = Constants.ONE_HUNDRED_INT;

    /**
     * getAsyncExecutor 方法是 默认的异步任务执行器
     *
     * @return 默认的异步任务执行器
     * @author rookie-spring
     * @date 2022/7/1 13:39
     */
    @Override
    public Executor getAsyncExecutor() {
        return buildCommonAsyncExecutor();
    }

    /**
     * asyncTaskExecutor 方法是 创建异步任务执行器 用于 @Async 注解的执行器
     *
     * @return 异步任务执行器实例
     * @author rookie-spring
     * @date 2022/7/1 10:17
     */
    @Bean("commonAsyncExecutor")
    public AsyncTaskExecutor buildCommonAsyncExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(DEFAULT_MAXIMUM_POOL_SIZE);
        asyncTaskExecutor.setCorePoolSize(DEFAULT_CORE_POOL_SIZE);
        asyncTaskExecutor.setThreadNamePrefix("common-async-executor-t");
        asyncTaskExecutor.setQueueCapacity(DEFAULT_CAPACITY);
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

}
