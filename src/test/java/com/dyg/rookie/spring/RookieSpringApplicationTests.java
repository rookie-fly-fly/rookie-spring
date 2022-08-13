package com.dyg.rookie.spring;

import com.dyg.rookie.spring.demo.controller.HelloController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RookieSpringApplicationTests {
    @Resource
    private HelloController helloController;

    @Test
    void contextLoads() {
        Assertions.assertThat(helloController.hello()).isEqualTo("Welcome to reactive ~");
    }

    @Test
    void contextLoads2() {
        Assertions.assertThat(helloController.hello1(111)).isEqualTo("Welcome to reactive ~" + 111);
    }
}
