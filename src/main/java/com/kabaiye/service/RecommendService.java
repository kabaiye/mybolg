package com.kabaiye.service;


import com.kabaiye.entity.vo.article.ArticleRecommendRes;

import java.util.List;

public interface RecommendService {
    /**
     * 添加推荐文章，使用redis
     */
    int saveRecommend(String articleId, Double score);

    /**
     * 删除推荐In redis
     */
    int deleteRecommend(String articleId);

    /**
     * 展示推荐文章
     */
    List<ArticleRecommendRes> infoRecommend();
}
