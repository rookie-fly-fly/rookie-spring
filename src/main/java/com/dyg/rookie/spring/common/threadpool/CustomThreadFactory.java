package com.dyg.rookie.spring.common.threadpool;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CustomThreadFactory 类是 自定义线程工厂
 *
 * @author rookie-spring
 * @date 2022/7/1 9:55
 */
public class CustomThreadFactory implements ThreadFactory {
    /**
     * 线程名称前缀
     */
    private final String threadNamePrefix;
    /**
     * 用来标识这是第几个生成的线程,被拼接在线程名称中
     */
    private final AtomicInteger counter = new AtomicInteger(1);

    /**
     * 自定义线程工厂
     *
     * @param threadNamePrefix 线程名称前缀
     */
    CustomThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    /**
     * newThread 方法是 创建新线程的方法
     *
     * @param runnable thread持有的Runnable对象
     * @return 新线程
     * @author rookie-spring
     * @date 2021/1/20 16:37
     */
    @Override
    public Thread newThread(@NotNull Runnable runnable) {
        // 线程名称=指定前缀-t1(从1到最大线程数)
        String threadName = threadNamePrefix + "-t" + counter.getAndIncrement();
        return new Thread(runnable, threadName);
    }
}
