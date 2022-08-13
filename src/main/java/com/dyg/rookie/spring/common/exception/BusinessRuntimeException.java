package com.dyg.rookie.spring.common.exception;

import com.dyg.rookie.spring.common.constants.CommonErrorCodeEnum;

/**
 * BusinessRuntimeException 类是 业务异常类
 *
 * @author rookie-spring
 * @date 2022/6/24 16:04
 */
public class BusinessRuntimeException extends RuntimeException {
    /**
     * 异常编码 默认值 “111111”
     */
    private final String code;
    /**
     * 异常描述
     */
    private final String message;

    /**
     * BusinessRuntimeException 的构造函数
     *
     * @param code    异常编码
     * @param message 异常描述
     * @author rookie-spring
     * @date 2022-06-24 10:59:00
     */
    public BusinessRuntimeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * BusinessRuntimeException 方法是 用于构造函数 异常编码采用默认值 “111111”
     *
     * @param message 异常描述
     * @author rookie-spring
     * @date 2022/6/24 16:05
     */
    public BusinessRuntimeException(String message) {
        this(CommonErrorCodeEnum.FAILED.getCode(), message);
    }

    /**
     * getCode 方法是 获取异常编码
     *
     * @return 异常编码
     * @author rookie-spring
     * @date 2022/6/24 16:35
     */
    public String getCode() {
        return code;
    }

    /**
     * getMessage 方法是 获取异常描述
     *
     * @return 异常描述
     * @author rookie-spring
     * @date 2022/6/24 16:36
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * fillInStackTrace 方法是 重写 Throwable 类的 fillInStackTrace 方法,不获取堆栈信息
     * 能有效的降低异常对性能的影响,验证结果见 readme 中的 “解决异常的性能问题” 模块
     *
     * @return 业务异常本身
     * @author rookie-spring
     * @date 2022/6/30 17:46
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        // 重写，禁止抓取堆栈信息
        return this;
    }

}
