/*
 * 版权所有 2009-2022Rookie Spring 保留所有权利。
 */
package com.dyg.rookie.spring.common.cache;

import com.dyg.rookie.spring.common.constants.CommonErrorCodeEnum;
import com.dyg.rookie.spring.common.resp.CallResponse;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存监控-Caffeine
 *
 * @author rookie-spring
 * @date 2022/9/9 13:29
 */
@Tag(name = "缓存监控")
@Slf4j
@RestController
@RequestMapping("monitor/caffeine")
public class MonitorCaffeineController {

    @Resource
    private CacheManager caffeineCacheManager;


    /**
     * 获取所有缓存的name
     *
     * @return {@link Collection<String> 所有的缓存name}
     * @author dongyinggang
     * @date 2022/9/9 15:48
     **/
    @GetMapping("cacheNames")
    @Operation(summary = "所有缓存name")
    public CallResponse<Collection<String>> cacheNames() {

        return CallResponse.success(caffeineCacheManager.getCacheNames());
    }

    /**
     * 根据缓存name获取缓存的内容
     *
     * @param cacheName :缓存的名称
     * @return 缓存内容
     * @author dongyinggang
     * @date 2022/9/9 15:49
     **/
    @GetMapping("cacheByName")
    @Operation(summary = "根据缓存name获取缓存的内容")
    public CallResponse<Map<Object, Object>> cacheByName(@RequestParam String cacheName) {

        CaffeineCache caffeineCache = (CaffeineCache) caffeineCacheManager.getCache(cacheName);
        if (caffeineCache == null) {
            throw CommonErrorCodeEnum.CACHE_NOT_EXIST.exception();
        }
        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = caffeineCache.getNativeCache();

        log.info("CacheName：{},缓存内容:{}", cacheName, cache.asMap());
        return CallResponse.success(cache.asMap());
    }

    /**
     * 根据缓存name清除缓存的内容
     *
     * @param cacheName :缓存名称
     * @return 缓存内容
     * @author dongyinggang
     * @date 2022/9/9 15:49
     **/
    @GetMapping("clearByName")
    @Operation(summary = "根据缓存name清除缓存的内容")
    public CallResponse<String> clearByName(@RequestParam String cacheName) {

        CaffeineCache caffeineCache = (CaffeineCache) caffeineCacheManager.getCache(cacheName);
        if (caffeineCache == null) {
            throw CommonErrorCodeEnum.CACHE_NOT_EXIST.exception();
        }
        caffeineCache.clear();

        return CallResponse.success("缓存清理成功");
    }

    /**
     * 根据缓存name查询缓存监控信息
     *
     * @param cacheName : 缓存名称
     * @return 缓存监控信息
     * @author dongyinggang
     * @date 2022/9/9 16:11
     **/
    @GetMapping("stats")
    @Operation(summary = "根据缓存name查询缓存监控信息")
    public CallResponse<Map<String, Object>> stats(@RequestParam String cacheName) {
        CaffeineCache caffeineCache = (CaffeineCache) caffeineCacheManager.getCache(cacheName);
        if (caffeineCache == null) {
            throw CommonErrorCodeEnum.CACHE_NOT_EXIST.exception();
        }
        CacheStats stats = caffeineCache.getNativeCache().stats();
        Map<String, Object> map = new HashMap<>();
        map.put("请求次数", stats.requestCount());
        map.put("命中次数", stats.hitCount());
        map.put("未命中次数", stats.missCount());
        map.put("加载成功次数", stats.loadSuccessCount());
        map.put("加载失败次数", stats.loadFailureCount());
        map.put("加载失败占比", stats.loadFailureRate());
        map.put("加载总耗时", stats.totalLoadTime());
        map.put("回收总次数", stats.evictionCount());
        map.put("回收总权重", stats.evictionWeight());

        return CallResponse.success(map);
    }
}
