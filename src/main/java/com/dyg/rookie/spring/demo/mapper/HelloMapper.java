/*
 * 版权信息
 */
package com.dyg.rookie.spring.demo.mapper;

import com.dyg.rookie.spring.demo.domain.DemoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * IHelloMapper 接口是 Mybatis的Mapper接口的样例
 *
 * @author rookie-spring
 * @module demo
 * @date 2022/7/1 13:29
 * @copyright All rights reserved
 */
@Mapper
public interface HelloMapper {

    /**
     * hello 方法是 Mapper方法demo
     *
     * @param id 请求参数
     * @return 查询结果
     * @author rookie-spring
     * @date 2022/7/1 17:24
     */
    DemoVO hello(Integer id);

    /**
     * insert 方法是 插入数据的示例
     *
     * @param name : 插入的名称
     * @return {@link int }
     * @author dongyinggang
     * @date 2023/6/30 13:38
     **/
    int insert(@Param("name") String name);
}
