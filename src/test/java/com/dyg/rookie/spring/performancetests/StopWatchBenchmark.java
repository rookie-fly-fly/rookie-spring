/*
 * 版权信息
 */
package com.dyg.rookie.spring.performancetests;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

/**
 * 计时器性能测试
 * 验证StopWatch对性能的影响，和通过System.currentTimeMillis()获取的耗时的方案进行比较
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
@Slf4j
public class StopWatchBenchmark {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(StopWatchBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    /**
     * useSystemThree 方法是 一个方法内通过System记录三段耗时
     *
     * @author rookie-spring
     * @date 2022/7/18 11:22
     */
    @Benchmark
    public void useSystemThree() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(10);
        log.info("耗时信息1:{}ms", System.currentTimeMillis() - start);
        long start1 = System.currentTimeMillis();
        Thread.sleep(10);
        log.info("耗时信息2:{}ms", System.currentTimeMillis() - start1);
        long start2 = System.currentTimeMillis();
        Thread.sleep(10);
        log.info("耗时信息3:{}ms", System.currentTimeMillis() - start2);
    }

    /**
     * 一个方法内通过 StopWatch 记录三段耗时
     *
     * @author rookie-spring
     * @date 2022/7/18 13:11
     **/
    @Benchmark
    public void useStopWatchThree() throws InterruptedException {
        StopWatch stopWatch = new StopWatch("StopWatch性能测试");
        stopWatch.start("任务1");
        Thread.sleep(10);
        stopWatch.stop();
        stopWatch.start("任务2");
        Thread.sleep(10);
        stopWatch.stop();
        stopWatch.start("任务3");
        Thread.sleep(10);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    /**
     * 一个方法内通过 System 记录1段耗时
     *
     * @author rookie-spring
     * @date 2022/7/18 13:15
     **/
    @Benchmark
    public void useSystemOne() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(10);
        log.info("耗时信息1:{}ms", System.currentTimeMillis() - start);
    }

    /**
     * 一个方法内通过 StopWatch 记录1段耗时
     *
     * @author rookie-spring
     * @date 2022/7/18 13:15
     **/
    @Benchmark
    public void useStopWatchOne() throws InterruptedException {
        StopWatch stopWatch = new StopWatch("StopWatch性能测试");
        stopWatch.start("任务1");
        Thread.sleep(10);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }
}
