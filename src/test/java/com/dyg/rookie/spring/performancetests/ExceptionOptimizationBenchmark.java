/*
 * 版权信息
 */
package com.dyg.rookie.spring.performancetests;

import com.dyg.rookie.spring.common.exception.BusinessRuntimeException;
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
 * 异常性能优化测试
 * 验证重写异常的 fillInStackTrace 方法可以有效降低异常的性能开销
 * 性能验证结果参见： 附录4：JMH基准测试工具
 *
 * @author rookie-spring
 * @module performance-tests
 * @date 2022/6/30 17:17
 */
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ExceptionOptimizationBenchmark {

    private static final int LIMIT = 10_000;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ExceptionOptimizationBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    /**
     * 创建一个对象
     *
     * @param blackhole : 用来消费产生的对象，防止对象被优化
     * @author rookie-spring
     * @date 2022/7/18 13:19
     **/
    @Benchmark
    public void doNotThrowException(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            blackhole.consume(new Object());
        }
    }

    /**
     * 创建一个 Exception 异常，抛出并捕获
     *
     * @param blackhole : 用来消费产生的对象，防止对象被优化
     * @author rookie-spring
     * @date 2022/7/18 13:19
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
     * 创建一个重写了 fillInStackTrace 方法的 BusinessRuntimeException 异常，抛出并捕获
     * 重写 fillInStackTrace 方法可以指定获取堆栈的方案，这里重写后不在获取堆栈信息
     *
     * @param blackhole : 用来消费产生的对象，防止对象被优化
     * @author rookie-spring
     * @date 2022/7/18 13:19
     **/
    @Benchmark
    public void throwAndCatchBusinessRuntimeException(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new BusinessRuntimeException("test");
            } catch (Exception e) {
                blackhole.consume(e);
            }
        }
    }


}
