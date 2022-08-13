/*
 * 版权信息
 */
package com.dyg.rookie.spring.demo.service.impl;

import com.dyg.rookie.spring.common.constants.CommonErrorCodeEnum;
import com.dyg.rookie.spring.common.exception.BusinessRuntimeException;
import com.dyg.rookie.spring.common.resp.CallResponse;
import com.dyg.rookie.spring.demo.constant.errorcode.DemoErrorCodeEnum;
import com.dyg.rookie.spring.demo.feign.DemoFeign;
import com.dyg.rookie.spring.demo.feign.domain.FeignQueryVO;
import com.dyg.rookie.spring.demo.feign.domain.FeignResponseVO;
import com.dyg.rookie.spring.demo.service.DemoFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * DemoFeign 的处理类
 *
 * @author rookie-spring
 * @module demo
 * @date 2022/7/1 13:47
 * @copyright All rights reserved
 */
@Slf4j
@Service
public class DemoFeignServiceImpl implements DemoFeignService {

    @Resource
    private DemoFeign demoFeign;

    /**
     * feignApiConvert 方法是 处理feign调用的返回值
     *
     * @param feignQueryVO 请求参数
     * @return 返回结果
     * @author rookie-spring
     * @date 2022/7/1 14:09
     */
    @Override
    public FeignResponseVO feignApiConvert(FeignQueryVO feignQueryVO) {
        CallResponse<FeignResponseVO> callResponse = demoFeign.feignApi(feignQueryVO);
        return Optional.ofNullable(callResponse)
                .filter(res -> CommonErrorCodeEnum.SUCCESS.getCode().equals(res.getCode())).map(CallResponse::getResult)
                .orElseThrow(() -> new BusinessRuntimeException(DemoErrorCodeEnum.DEMO_ERROR_CODE.getCode(),
                        DemoErrorCodeEnum.DEMO_ERROR_CODE.getMsg()
                ));

    }
}
