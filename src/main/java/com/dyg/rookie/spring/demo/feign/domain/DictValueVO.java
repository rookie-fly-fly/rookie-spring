/*
 * 版权信息
 */
package com.dyg.rookie.spring.demo.feign.domain;

import lombok.Data;

/**
 * DictValue 类是 字典管理的字典值对象
 *
 * @author rookie-spring
 * @module demo
 * @date 2022/7/1 14:32
 * @copyright All rights reserved
 */
@Data
public class DictValueVO {

    /**
     * 字典值id
     */
    private Integer valueId;

    /**
     * 所属字典类型
     */
    private String typeCode;

    /**
     * 字典项名称
     */
    private String valueName;

    /**
     * 字典项code
     */
    private String valueCode;

    /**
     * 父级字典值
     */
    private Integer parentId;

    /**
     * 字典项描述
     */
    private String valueDesc;
}
