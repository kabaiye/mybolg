package com.kabaiye.entity.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
{
    "records": [
      {
        "id": 1,
        "original": 1,
        "categoryName": "一级分类一",
        "categoryId": 1,
        "title": "标题",
        "summary": "摘要",
        "cover": "http:www.baidu.com",
        "status": 1,
        "viewCount": 0,
        "commentCount": 0,
        "likeCount": 0,
        "collectCount": 0,
        "publishTime": "2019-12-31 17:53:49",
        "updateTime": "2019-12-31 17:53:49",
        "user": {
          "id": 1,
          "nickname": "小管家",
          "avatar": "https://poile-img.nos-eastchina1.126.net/me.png"
        },
        "tagList": [
          {
            "id": 1,
            "name": "测试"
          }
        ]
      }
    ],
    "total": 1,
    "size": 5,
    "current": 1,
    "searchCount": true,
    "pages": 1
  }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoRes{
    private List<InfoArticleRes> records;

    private Long total;

    private Integer size;

    private Integer current;

    private Boolean searchCount;

    private Integer pages;
}
