package com.dyg.rookie.spring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Swagger3的相关配置类
 * 解决springboot2.7.0 和springfox不兼容问题 Failed to start bean ‘
 * * documentationPluginsBootstrapper ‘ ; nested exception…
 *
 * @author rookie-spring
 * @date 2022/7/19 16:23
 * @copyright All rights reserved
 */
public class Swagger3BeanPostProcessorVO implements BeanPostProcessor {

    /**
     * 实例化后的相关操作
     *
     * @param bean     : 对象
     * @param beanName : 对象名称
     * @return {@link Object }
     * @author rookie-spring
     * @date 2022/7/19 16:25
     **/
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
            customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
        }
        return bean;
    }

    /**
     * 去除了 RequestMapping 中PatternParser为null的内容
     *
     * @param mappings : RequestMapping
     * @author rookie-spring
     * @date 2022/7/19 16:47
     **/
    private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(
            List<T> mappings) {
        List<T> copy = mappings.stream().filter(mapping -> mapping.getPatternParser() == null)
                .collect(Collectors.toList());
        mappings.clear();
        mappings.addAll(copy);
    }

    private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
        try {
            Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
            assert field != null;
            //由于异味的问题，启用黑魔法，此处的注释不可以删除。
            field.setAccessible(true);
            return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
