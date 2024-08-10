package com.kabaiye.service;

public interface LikeService {
    /**
     * 点赞
     */
    int addLike(Integer userId, String articleId);

    /**
     * 取消点赞
     */
    int cancelLike(Integer userId, String articleId);

    /**
     * 是否点赞
     */
    int isLiked(Integer userId, String articleId);
}
