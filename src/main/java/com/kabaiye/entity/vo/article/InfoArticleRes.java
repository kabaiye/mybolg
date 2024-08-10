package com.kabaiye.entity.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kabaiye.entity.pojo.Tag;
import com.kabaiye.entity.vo.user.ArticleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import static com.kabaiye.entity.constant.DateConstant.DATE_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoArticleRes {

    private String id;

    private Integer original;

    private String categoryName;

    private Integer categoryId;

    private String title;

    private String summary;

    private String content;

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
}

