/*
 * 版权信息
 */
package com.dyg.rookie.spring.demo.service;

import com.dyg.rookie.spring.demo.domain.DemoVO;
import com.dyg.rookie.spring.demo.feign.domain.FeignQueryVO;
import com.dyg.rookie.spring.demo.feign.domain.FeignResponseVO;

/**
 * 示例Service接口
 *
 * @author rookie-spring
 * @module demo
 * @date 2022/7/1 13:17
 * @copyright All rights reserved
 */
public interface IHelloService {

    /**
     * hello 方法是 请求数据库demo
     *
     * @param id id值
     * @return demo对象
     * @author rookie-spring
     * @date 2022/7/1 17:52
     */
    DemoVO hello(Integer id);

    /**
     * asyncDemo 方法是 异步方法的示例
     *
     * @author rookie-spring
     * @date 2022/7/1 13:40
     */
    void asyncDemo();

    /**
     * feignDemo 方法是 feign调用示例
     *
     * @param feignQueryVO feign请求对象
     * @return 目标字典转换的map
     * @author rookie-spring
     * @date 2022/7/1 14:29
     */
    FeignResponseVO feignDemo(FeignQueryVO feignQueryVO);

    /**
     * 测试@Transtonal 注解
     *
     * @param id : id值
     * @param name : 修改的名称
     * @return {@link String }
     * @author dongyinggang
     * @date 2023/6/30 13:24
     **/
    String transactionalDemo(int id, String name);
}
