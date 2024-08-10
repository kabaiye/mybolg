package com.kabaiye.entity.pojo;

import lombok.Data;

/**
 * 文章收藏实体类
 */
@Data
public class Collect {

    /**
     * 收藏记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文章ID
     */
    private Long articleId;
}