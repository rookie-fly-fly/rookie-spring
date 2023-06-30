package com.dyg.rookie.spring.common.constants;

/**
 * 类的说明  业务的枚举值
 *
 * @module
 * @Author rookie-spring
 * @Date 2022/7/11 15:03
 */
public enum BusinessCodeEnum {

    /**
     * A:模块下业务代号
     */
    //E 公共模块
    COMMON_MODULE("A"),
    ;

    private final String code;

    BusinessCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
