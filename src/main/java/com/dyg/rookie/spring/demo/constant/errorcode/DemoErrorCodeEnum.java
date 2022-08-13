package com.dyg.rookie.spring.demo.constant.errorcode;

import com.dyg.rookie.spring.common.constants.CommonErrorCodeHelper;

/**
 * 示例错误码
 *
 * @author rookie-spring
 * @module demo
 * @date 2022/7/1 13:57
 * @copyright All rights reserved
 */
public enum DemoErrorCodeEnum implements CommonErrorCodeHelper {

    /**
     * AA-0-A-[01,99] 订单模块-参数错误-示例业务 错误码 AA:订单模块（订单模块、工单模块等,对应关系在错误码文件中查看）
     * 0:错误类型（参数错误、逻辑错误、调用错误、解析错误、系统异常） A:模块下业务代号（A:服务商、B:订单、C:对账） [01,99]:错误码顺序
     */
    DEMO_ERROR_CODE("AA0A01", "错误码示例");

    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误信息
     */
    private final String msg;

    /**
     * McErrorCodeEnum 方法是 构造方法
     *
     * @param code    错误码
     * @param message 错误信息
     * @author rookie-spring
     * @date 2022/7/1 13:57
     */
    DemoErrorCodeEnum(String code, String message) {
        this.code = code;
        msg = message;
    }

    /**
     * getCode 方法是 获取错误码
     *
     * @return 错误码
     * @author rookie-spring
     * @date 2022/7/1 13:57
     */
    @Override
    public String getCode() {
        return code;
    }


    /**
     * getMessage 方法是 获取错误信息
     *
     * @return 错误信息
     * @author rookie-spring
     * @date 2020/12/15 19:58
     */
    @Override
    public String getMsg() {
        return msg;
    }
}
