package com.dyg.rookie.spring.demo.constant;

import com.dyg.rookie.spring.common.constants.CommonErrorCodeHelper;

/**
 * Constants 类是 常量类
 *
 * @author rookie-spring
 * @date 2020/10/30 8:50
 */
public enum DemoConstants implements CommonErrorCodeHelper {
    /**
     * 常量类
     */
    BATCH_CONFIG_CHUNK_SIZE("500", "批次的大小"),
    ;

    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误信息
     */
    private final String msg;

    DemoConstants(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
