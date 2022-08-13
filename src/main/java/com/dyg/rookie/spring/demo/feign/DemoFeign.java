/*
 * 版权信息
 */
package com.dyg.rookie.spring.demo.feign;

import com.dyg.rookie.spring.common.resp.CallResponse;
import com.dyg.rookie.spring.demo.feign.domain.FeignQueryVO;
import com.dyg.rookie.spring.demo.feign.domain.FeignResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign 调用示例
 *
 * @author rookie-spring
 * @module demo
 * @date 2022/7/1 13:43
 * @copyright All rights reserved
 */
@FeignClient(name = "SYSTEM", url = "${feign.url.system}")
public interface DemoFeign {

    /**
     * feignApi 方法是 调用 system 服务的接口
     *
     * @param feignQueryVO 请求对象
     * @return 响应对象
     * @author rookie-spring
     * @date 2022/7/1 14:15
     */
    @PostMapping("/demo/feign")
    CallResponse<FeignResponseVO> feignApi(@RequestBody FeignQueryVO feignQueryVO);

}
