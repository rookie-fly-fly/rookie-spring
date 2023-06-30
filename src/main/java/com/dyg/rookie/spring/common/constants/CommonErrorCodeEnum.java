package com.dyg.rookie.spring.common.constants;

/**
 * CommonErrorCodeEnum 类是 公共错误码枚举类
 * AA-0-A-[01,99] rookie模块-参数错误-示例业务-错误码顺序
 * 前两位：模块  AA:rookie模块【对应关系在错误码文件中查看】
 * 第三位：错误类型 【0：参数错误、1：逻辑错误:2：调用错误:3：解析错误:4：系统异常】
 * 第四位：模块下的业务代号 【A:模块A、B:模块B、C:模块C】
 * 第五六位：[01,99]:错误码顺序
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
    SYSTEM_ERROR("111111", "系统异常,请联系管理员"),

    //公共模块->参数错误[0] --> 同类的编码，需要按组，按顺序排列，不能重复

    //公共模块->逻辑错误[1] --> 同类的编码，需要按组，按顺序排列，不能重复
    CACHE_NOT_EXIST("目标缓存不存在", "01", ErrorTypeCodeEnum.LOGIC_ERROR),

    //公共模块->调用错误[2] --> 同类的编码，需要按组，按顺序排列，不能重复

    //公共模块->解析错误[3] --> 同类的编码，需要按组，按顺序排列，不能重复

    //公共模块->系统错误[4] --> 同类的编码，需要按组，按顺序排列，不能重复
    ;


    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误描述
     */
    private final String msg;

    /**
     * 错误码模块,通过字典值生成错误码。
     */
    CommonErrorCodeEnum(String message, String code, ErrorTypeCodeEnum errorTypeCodeEnum) {
        this.code =
                ModuleCodeEnum.ROOKIE_MODULE.getCode() + errorTypeCodeEnum.getCode() + BusinessCodeEnum.COMMON_MODULE.getCode() + code;
        msg = message;
    }


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
