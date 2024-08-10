package com.kabaiye.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kabaiye.entity.vo.comment.CommentReply;
import com.kabaiye.entity.vo.user.ArticleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.kabaiye.entity.constant.DateConstant.DATE_FORMAT;

/**
 * 文章评论实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleComment {

    /**
     * 评论ID
     */
    private String id;

    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 评论者ID
     */
    private ArticleUser fromUser;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime commentTime;

    /**
     * 是否删除，1: 是，0: 否
     */
    private Integer deleted;

}