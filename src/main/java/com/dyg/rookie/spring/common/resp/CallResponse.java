package com.dyg.rookie.spring.common.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * CallResponse 类是 公共通用返回类
 *
 * @author rookie-spring
 * @date 2022/6/24 16:07
 */
@Schema(description = "公共通用返回类")
@Data
public class CallResponse<T> {
    /**
     * 默认成功编码 “000000”
     */
    private static final String SUCCESS_CODE = "000000";
    /**
     * 默认成功描述 “成功”
     */
    private static final String SUCCESS_MESSAGE = "成功";
    /**
     * 默认失败编码 “111111”
     */
    private static final String ERROR_CODE = "111111";
    /**
     * 返回值编码
     */
    @Schema(description = "返回值编码")
    private String code;
    /**
     * 返回值说明
     */
    @Schema(description = "返回值说明")
    private String message;
    /**
     * 返回值
     */
    @Schema(description = "返回值")
    private T result;

    /**
     * CallResponse 方法是 构造方法
     *
     * @param code    编码
     * @param message 描述
     * @author rookie-spring
     * @date 2022/6/24 16:38
     */
    private CallResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * CallResponse 方法是 构造方法
     *
     * @param code    编码
     * @param message 描述
     * @param result  返回值
     * @author rookie-spring
     * @date 2022/6/24 16:38
     */
    private CallResponse(String code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    /**
     * CallResponse 方法是 无参构造方法
     *
     * @author rookie-spring
     * @date 2022/6/24 16:39
     */
    public CallResponse() {
        super();
    }

    /**
     * 需要返回成功时候调用
     *
     * @param result 返回的数据对象
     * @return 构建的统一返回对象
     * @author rookie-spring
     * @date 2022/6/24 16:08
     */
    public static <T> CallResponse<T> success(T result) {
        return new CallResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, result);
    }

    /**
     * 返回成功但是需要定义提示信息
     *
     * @param message 提示信息
     * @param result  返回的数据对象
     * @return 构建的统一返回对象
     * @author rookie-spring
     * @date 2022/6/24 16:12
     */
    public static <T> CallResponse<T> success(String message, T result) {
        return new CallResponse<>(SUCCESS_CODE, message, result);
    }

    /**
     * success 方法是 自定义返回码 返回信息
     *
     * @param code    返回码
     * @param message 返回信息
     * @param result  返回的数据对象
     * @return 构建的统一返回对象
     * @author rookie-spring
     * @date 2022/6/24 16:13
     */
    public static <T> CallResponse<T> success(String code, String message, T result) {
        return new CallResponse<>(code, message, result);
    }

    /**
     * error 方法是 当返回业务失败时，需要额外的增加额外的code和msg
     *
     * @param code    返回码
     * @param message 返回信息
     * @return 构建的统一返回对象
     * @author rookie-spring
     * @date 2022/6/24 16:13
     */
    public static <T> CallResponse<T> error(String code, String message) {
        return new CallResponse<>(code, message);
    }

    /**
     * error 方法是 用于返回业务失败
     *
     * @param message 提示信息
     * @return 构建的统一返回对象
     * @author rookie-spring
     * @date 2022/6/24 16:14
     */
    public static <T> CallResponse<T> error(String message) {
        return new CallResponse<>(ERROR_CODE, message);
    }

    /**
     * getSuccessCode 方法是 获取成功编码
     *
     * @return 默认成功编码
     * @author rookie-spring
     * @date 2022/6/24 16:42
     */
    public static String getSuccessCode() {
        return SUCCESS_CODE;
    }

    /**
     * getErrorCode 方法是 获取失败的默认code值
     *
     * @return 失败的默认code值
     * @author rookie-spring
     * @date 2022/6/24 16:40
     */
    public static String getErrorCode() {
        return ERROR_CODE;
    }

    /**
     * toString 方法是 获取返回值的字符串
     *
     * @return 字符串信息
     * @author rookie-spring
     * @date 2022/6/24 16:40
     */
    @Override
    public String toString() {
        return "CallResponse [code=" + code + ", message=" + message + ", result=" + result + "]";
    }
}
