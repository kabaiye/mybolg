package com.kabaiye.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

import static com.kabaiye.entity.constant.DateConstant.DATE_FORMAT;

/**
 * 文章。
 */
@Data
public class Article {

    /**
     * 文章ID。
     */
    private String id;

    /**
     * 是否原创，1: 是，0: 否。
     */
    private Integer original;

    /**
     * 用户ID。
     */
    private Integer userId;

    /**
     * 分类ID。
     */
    private Integer categoryId;

    /**
     * 分类名称 - 冗余字段。
     */
    private String categoryName;

    /**
     * 文章标题。
     */
    private String title;

    /**
     * 文章摘要。
     */
    private String summary;

    /**
     * 文章内容。
     */
    private String content;

    /**
     * 文章富文本内容。
     */
    private String htmlContent;

    /**
     * 文章状态：0 为正常，1 为待发布，2 为回收站。
     */
    private Integer status;

    /**
     * 文章浏览次数。
     */
    private Integer viewCount;

    /**
     * 评论数 - 冗余字段。
     */
    private Integer commentCount;

    /**
     * 点赞数 - 冗余字段。
     */
    private Integer likeCount;

    /**
     * 收藏数 - 冗余字段。
     */
    private Integer collectCount;

    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime publishTime;


    private LocalDateTime updateTime;
    /**
     * 转载地址。
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String reproduceUrl;

    /**
     * 是否已删除，1: 是，0: 否。
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer isDeleted;
}
