package com.dyg.rookie.spring.common.constants;

import cn.hutool.core.util.NumberUtil;
import com.dyg.rookie.spring.common.exception.BusinessRuntimeException;

/**
 * 将一个错误消息组装成一个错误对象返回，方便异常
 *
 * @module
 * @Author rookie-spring
 * @Date 2022/7/8 11:10
 */
public interface CommonErrorCodeHelper {
    /**
     * 针对异常枚举类，便捷生成异常类
     *
     * @return
     */
    default BusinessRuntimeException exception() {
        return new BusinessRuntimeException(getCode(), getMsg());
    }

    /**
     * 当枚举类   用于存放常量的时候，通过此方法来转换code到int类型数据
     * @return
     */
    default int intValue() {
        return intValue(0);
    }
    /**
     * 当枚举类   用于存放常量的时候，通过此方法来转换code到int类型数据
     * @return
     */
    default int intValue(int defaultNumber) {
        if(NumberUtil.isNumber(getCode())){
            return NumberUtil.parseInt(getCode());
        }
        return defaultNumber;
    }
    String getCode();

    String getMsg();
}
