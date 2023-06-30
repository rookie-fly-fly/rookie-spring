package com.dyg.rookie.spring.common.constants;

/**
 * 类的说明 模块的枚举
 *
 * @module
 * @Author rookie-spring
 * @Date 2022/7/11 15:03
 */
public enum ModuleCodeEnum {

    /**
     * AA-0-A-[01,99] 菜鸟模块-参数错误-示例业务 错误码 AA:菜鸟模块（菜鸟模块等,对应关系在错误码文件中查看）
     * 0:错误类型（参数错误、逻辑错误、调用错误、解析错误、系统异常） A:模块下业务代号 [01,99]:错误码顺序
     */
    ROOKIE_MODULE("AA");

    private final String code;

    ModuleCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
