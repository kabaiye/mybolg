package com.kabaiye.entity.pojo;

import lombok.Data;


/**
 * 文章点赞关联实体类
 */
@Data
public class ArticleLike{
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 用户ID
     */
    private Long userId;
}