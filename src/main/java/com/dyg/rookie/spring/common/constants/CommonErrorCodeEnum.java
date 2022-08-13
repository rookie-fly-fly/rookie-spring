package com.dyg.rookie.spring.common.constants;

/**
 * CommonErrorCodeEnum 类是 公共错误码常量类
 *
 * @author rookie-spring
 * @date 2022/6/29 10:35
 */

public enum CommonErrorCodeEnum implements CommonErrorCodeHelper {
    /**
     * 成功
     */
    SUCCESS("000000", "成功"),
    
    /**
     * 失败
     */
    FAILED("111111", "失败"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR("111111", "系统异常,请联系管理员");

    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误描述
     */
    private final String msg;


    /**
     * 构造函数
     *
     * @param code 错误码
     * @param msg  错误描述
     */
    CommonErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据错误码获取错误对象
     *
     * @return 错误对象
     */
    public static CommonErrorCodeEnum getByCode(String code) {
        if (null == code) {
            return null;
        }
        for (CommonErrorCodeEnum commonErrorCodeEnum : values()) {

            if (commonErrorCodeEnum.getCode().equals(code)) {
                return commonErrorCodeEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getMsg();
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
