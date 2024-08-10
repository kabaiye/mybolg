package com.kabaiye.entity.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveArticleBody {
    /**
     * 分类ID
     */
    private String categoryId;
    /**
     * 分类名字
     */
    private String categoryName;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章富文本内容
     */
    private String htmlContent;
    /**
     * 封面图片URL
     */
    private String cover;
    /**
     * 文章ID
     */
    private String id;
    /**
     * 是否原创标志，1: 是，0: 否
     */
    private String original;
    /**
     * 转载地址
     */
    private String reproduce;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 标签ID列表
     */
    private List<String> tagIds;
    /**
     * 文章标题
     */
    private String title;
}
