package com.dyg.rookie.spring.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 类的说明
 *
 * @module
 * @Author rookie-spring
 * @Date 2022/6/24 13:14
 */
@Data
public class BaseEntity {

    /**
     * 当前页码
     */
    private Integer page = 1;
    /**
     * 每页的大小
     */
    private Integer size = 1;
    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
