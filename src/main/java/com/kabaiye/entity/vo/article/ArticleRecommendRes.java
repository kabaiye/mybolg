package com.kabaiye.entity.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabaiye.entity.pojo.Tag;
import com.kabaiye.entity.vo.user.ArticleUser;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static com.kabaiye.entity.constant.DateConstant.DATE_FORMAT;

@Data
public class ArticleRecommendRes {

    private String id;

    private Integer original;

    private String categoryName;

    private Integer categoryId;

    private String title;

    private String summary;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String content;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String htmlContent;

    private String cover;

    private Integer status;

    private Integer viewCount;

    private Integer commentCount;

    private Integer likeCount;

    private Integer collectCount;

    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime publishTime;

    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime updateTime;

    private ArticleUser user;

    private List<Tag> tagList;

    private Double recommendScore;
}
