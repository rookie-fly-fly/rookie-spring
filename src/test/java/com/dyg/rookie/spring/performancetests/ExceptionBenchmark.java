/*
 * 版权信息
 */
package com.dyg.rookie.spring.performancetests;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 异常性能测试
 * 验证异常对性能的影响
 * 性能验证结果参见： 附录4：JMH基准测试工具
 *
 * @author rookie-spring
 * @module performance-tests
 * @date 2022/6/30 10:56
 */
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ExceptionBenchmark {
    private static final int LIMIT = 10_000;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ExceptionBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    /**
     * 创建一个 object 对象
     *
     * @param blackhole : 用来消费产生的对象，防止对象被优化
     * @author rookie-spring
     * @date 2022/7/18 13:17
     **/
    @Benchmark
    public void doNotThrowException(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            blackhole.consume(new Object());
        }
    }

    /**
     * 创建一个 Exception 异常并进行抛出和捕获
     *
     * @param blackhole : 用来消费产生的对象，防止对象被优化
     * @author rookie-spring
     * @date 2022/7/18 13:18
     **/
    @Benchmark
    public void throwAndCatchException(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * 创建一个异常
     *
     * @param blackhole : 用来消费产生的对象，防止对象被优化
     * @author rookie-spring
     * @date 2022/7/18 13:19
     **/
    @Benchmark
    public void createExceptionWithoutThrowingIt(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            blackhole.consume(new Exception());
        }
    }

    /**
     * 创建一个异常，但通过JVM参数设置该异常不获取堆栈信息
     *
     * @param blackhole : 用来消费产生的对象，防止对象被优化
     * @author rookie-spring
     * @date 2022/7/18 13:19
     **/
    @Benchmark
    @Fork(value = 1, jvmArgs = "-XX:-StackTraceInThrowable")
    public void throwExceptionWithoutAddingStackTrace(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * 创建一个异常，抛出并捕获，并打印异常堆栈信息
     *
     * @param blackhole : 用来消费产生的对象，防止对象被优化
     * @author rookie-spring
     * @date 2022/7/18 13:19
     **/
    @Benchmark
    public void throwExceptionAndUnwindStackTrace(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
                blackhole.consume(e.getStackTrace());
            }
        }
    }
}
