package com.kabaiye.service.impl;

import com.kabaiye.mapper.ArticleMapper;
import com.kabaiye.mapper.LikeMapper;
import com.kabaiye.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    @Transactional
    public int addLike(Integer userId, String articleId) {
        articleMapper.addLike(articleId);
        return likeMapper.addLike(userId,articleId);
    }

    @Override
    @Transactional
    public int cancelLike(Integer userId, String articleId) {
        articleMapper.reduceCollect(articleId);
        return likeMapper.cancelLike(userId,articleId);
    }

    @Override
    public int isLiked(Integer userId, String articleId) {
        return likeMapper.isLiked(userId,articleId);
    }
}
