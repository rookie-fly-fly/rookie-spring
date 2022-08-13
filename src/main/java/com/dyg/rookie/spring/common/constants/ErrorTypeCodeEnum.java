package com.dyg.rookie.spring.common.constants;

/**
 * 类的说明  错误代码中的，元素枚举信息
 *
 * @module
 * @Author rookie-spring
 * @Date 2022/7/11 15:03
 */
public enum ErrorTypeCodeEnum {

    /**
     * AA-0-A-[01,99] 订单模块-参数错误-示例业务 错误码 AA：spring示例模块（spring示例、java基础类模块等,对应关系在错误码文件中查看）
     * 0:错误类型（0 参数错误、1 逻辑错误、2调用错误、3解析错误、4系统异常） A:模块下业务代号（A:模块A、B:模块B、C:模块C） [01,99]:错误码顺序
     */
    PARAM_ERROR("0"),
    //逻辑错误
    LOGIC_ERROR("1"),
    //调用错误
    CALL_ERROR("2"),
    //解析错误
    PARSE_ERROR("3"),
    //系统异常
    SYSTEM_ERROR("4");

    private final String code;

    ErrorTypeCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
