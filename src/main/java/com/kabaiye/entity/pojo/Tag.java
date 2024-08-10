package com.kabaiye.entity.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Tag {
    /**
     * Tag id
     */
    private String id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 是否删除，1：是，0：否
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer deleted;
}
