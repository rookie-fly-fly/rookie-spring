package com.dyg.rookie.spring.config;

import com.dyg.rookie.spring.common.constants.Constants;
import com.dyg.rookie.spring.common.threadpool.ThreadPoolBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

/**
 * ThreadPoolConfig 类是 定义项目中的线程池
 *
 * @author rookie-spring
 * @date 2021-01-12 15:06
 **/
@Configuration
public class ThreadPoolConfig {

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
     * commonThreadPool 方法是 创建公共线程池,用于所有业务
     *
     * @return 公共线程池
     * @author rookie-spring
     * @date 2021/1/12 10:58
     */
    @Bean("commonThreadPool")
    public ExecutorService buildCommonThreadPool() {
        String prefix = "common";
        return ThreadPoolBuilder.build(prefix, DEFAULT_CORE_POOL_SIZE, DEFAULT_MAXIMUM_POOL_SIZE, DEFAULT_CAPACITY);
    }
}
