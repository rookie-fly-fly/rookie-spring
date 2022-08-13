package com.dyg.rookie.spring;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * RookieSpringApplication 类是 rookie-spring 项目启动类
 * 启用了feign统一日志
 *
 * @author rookie-spring
 * @date 2022/7/1 15:38
 */
@EnableFeignClients
@EnableTransactionManagement
@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
public class RookieSpringApplication {
    /**
     * 主动获取环境变量的方法
     */
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        //启动类
        SpringApplication.run(RookieSpringApplication.class, args);
    }
}
