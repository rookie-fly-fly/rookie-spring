/*
 * 版权所有 2009-2022Rookie Spring 保留所有权利。
 */
package com.dyg.rookie.spring.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 自定义key生成器
 *
 * @author dongyinggang
 * @date 2022/9/9 10:32
 */
@Slf4j
@Component
public class CacheCustomKeyGenerator implements KeyGenerator {

    /**
     * key生成方法
     *
     * @param target : 类对象实例
     * @param method : 方法名称
     * @param params : 参数列表
     * @return {@link Object }
     * @author dongyinggang
     * @date 2022/9/9 10:35
     **/
    @Override
    public Object generate(Object target, Method method, Object... params) {
        String key = String.format("%s-%s:%s", target.getClass().getSimpleName(), method.getName(),
                StringUtils.arrayToDelimitedString(params, "_"));
        log.info("缓存生成的key:{}", key);
        return key;
    }
}
