package com.dyg.rookie.spring.common.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolBuilder 类是 线程池构造器
 *
 * @author rookie-spring
 * @date 2021-01-12 10:44
 **/
public final class ThreadPoolBuilder {

    private ThreadPoolBuilder() {
        throw new IllegalStateException("Utility class" );
    }

    /**
     * build 方法是 创建线程池
     *
     * @param prefix          线程名称前缀
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param capacity        任务队列长度
     * @return 线程池对象
     * @author rookie-spring
     * @date 2021/1/12 10:52
     */
    public static ExecutorService build(String prefix, int corePoolSize, int maximumPoolSize, int capacity) {
        int keepAlive = 0;
        // 传入线程的名称前缀,创建线程工厂
        ThreadFactory factory = new CustomThreadFactory(prefix);
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAlive, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(capacity), factory
        );
    }

}
