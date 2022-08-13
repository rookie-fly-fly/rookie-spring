package com.dyg.rookie.spring.demo.service;

import com.dyg.rookie.spring.demo.feign.domain.FeignQueryVO;
import com.dyg.rookie.spring.demo.feign.domain.FeignResponseVO;

/**
 * DemoFeignService
 *
 * @author rookie-spring
 * @date 2022/8/1 14:18
 */
public interface DemoFeignService {

    /**
     * feignApiConvert 方法是 处理feign调用的返回值
     *
     * @param feignQueryVO 请求参数
     * @return 返回结果
     * @author rookie-spring
     * @date 2022/7/1 14:09
     */
    FeignResponseVO feignApiConvert(FeignQueryVO feignQueryVO);
}

