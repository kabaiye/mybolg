package com.kabaiye.entity.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章回复实体类
 */
@Data
public class ArticleReply {

    /**
     * 回复ID
     */
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 回复者ID
     */
    private Long fromUserId;

    /**
     * 被回复者ID
     */
    private Long toUserId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复时间
     */
    private LocalDateTime replyTime;

    /**
     * 是否删除，1: 是，0: 否
     */
    private Integer deleted;
}