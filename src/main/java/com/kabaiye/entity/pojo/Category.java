package com.kabaiye.entity.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 代表数据库中的分类表。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category{

    /**
     * 分类ID。
     */
    private Integer id;

    /**
     * 分类名称。
     */
    private String name;

    /**
     * 父分类ID。对于不需要树形结构的分类，默认值为0。
     */
    private Integer parentId;

    /**
     * 是否已删除，1: 是，0: 否。
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer isDeleted;
}
