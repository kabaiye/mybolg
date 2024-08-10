package com.kabaiye.entity.pojo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleTag {
    Integer id;
    Integer articleId;
    List<Tag> tags;
}
