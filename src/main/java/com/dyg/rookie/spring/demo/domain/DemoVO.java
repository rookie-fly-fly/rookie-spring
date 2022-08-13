/*
 * 版权信息
 */
package com.dyg.rookie.spring.demo.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * DemoVO 实例VO对象
 *
 * @author rookie-spring
 * @module demo
 * @date 2022/6/27 10:44
 * @copyright All rights reserved
 */
@Schema(description = "DemoVO 实例VO对象")
@Data
public class DemoVO {
    /**
     * 名称
     */
    @Schema(description = "名称")
    @Length(min = 1, max = 20, message = "名称长度必须在1-20之间")
    @NotBlank(message = "名称不能为空")
    private String name;
    /**
     * 年龄 范围[1,120]
     */
    @Schema(description = "年龄 范围[1,120]")
    @Range(min = 1, max = 120, message = "年龄必须在1-120之间")
    @NotNull(message = "年龄不能为空")
    private Integer age;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    private String email;
    /**
     * 要检验的list
     */
    @Schema(description = "要检验的list")
    @NotNull(message = "要检验的list不能为空")
    @Size(min = 1, max = 20, message = "list长度必须在1-20之间")
    private List<String> list;
    /**
     * 嵌套对象
     */
    @Schema(description = "嵌套对象")
    @NotNull(message = "嵌套对象不能为空")
    @Valid
    private NestedVO nestedVO;
}
