/*
 * 版权信息
 */
package com.dyg.rookie.spring.demo.service.impl;

import com.dyg.rookie.spring.demo.domain.DemoVO;
import com.dyg.rookie.spring.demo.feign.domain.FeignQueryVO;
import com.dyg.rookie.spring.demo.feign.domain.FeignResponseVO;
import com.dyg.rookie.spring.demo.mapper.HelloMapper;
import com.dyg.rookie.spring.demo.service.DemoFeignService;
import com.dyg.rookie.spring.demo.service.IHelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * HelloServiceImpl 接口是 Service接口的实现类 示例
 *
 * @author rookie-spring
 * @module demo
 * @date 2022/7/1 13:18
 * @copyright All rights reserved
 */
@Slf4j
@Service
public class HelloServiceImpl implements IHelloService {

    @Autowired
    private HelloMapper helloMapper;
    @Autowired
    private DemoFeignService demoFeignService;

    /**
     * hello 方法是 请求数据库demo
     *
     * @param id id值
     * @return demo对象
     * @author rookie-spring
     * @date 2022/7/1 17:52
     */
    @Override
    public DemoVO hello(Integer id) {
        return helloMapper.hello(id);
    }

    /**
     * asyncDemo 方法是 异步方法的示例
     *
     * @author rookie-spring
     * @date 2022/7/1 13:40
     */
    @Async
    @Override
    public void asyncDemo() {
        log.info("当前执行线程来自默认异步任务执行器，{}", Thread.currentThread().getName());
    }

    /**
     * feignDemo 方法是 feign调用示例
     *
     * @param feignQueryVO feign请求对象
     * @return 目标字典转换的map
     * @author rookie-spring
     * @date 2022/7/1 14:29
     */
    @Override
    public FeignResponseVO feignDemo(FeignQueryVO feignQueryVO) {
        return demoFeignService.feignApiConvert(feignQueryVO);
    }

    /**
     * 测试@Transtonal 注解
     *
     * @param id : id值
     * @param name : 修改的名称
     * @return {@link String }
     * @author dongyinggang
     * @date 2023/6/30 13:24
     **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String transactionalDemo(int id, String name) {
        helloMapper.insert(name);
        return helloMapper.hello(id).getName();
    }
}
