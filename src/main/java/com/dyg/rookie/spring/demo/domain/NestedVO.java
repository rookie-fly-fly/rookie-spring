/*
 * 版权信息
 */
package com.dyg.rookie.spring.demo.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 嵌套对象
 *
 * @author rookie-spring
 * @module demo
 * @date 2022/6/28 8:52
 * @copyright All rights reserved
 */
@Schema(description = "嵌套对象")
@Data
public class NestedVO {
    /**
     * 嵌套对象的名称
     */
    @Schema(description = "嵌套对象的名称")
    @Length(min = 1, max = 20, message = "嵌套对象的名称长度必须在1-20之间")
    @NotBlank(message = "嵌套对象的名称不能为空")
    private String nestedName;
}
