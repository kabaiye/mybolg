package com.kabaiye.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kabaiye.entity.vo.article.ArticleRecommendRes;
import com.kabaiye.entity.vo.article.InfoArticleRes;
import com.kabaiye.mapper.ArticleMapper;
import com.kabaiye.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.kabaiye.entity.constant.UserConstant.RECOMMEND_REDIS_KEY;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public int saveRecommend(String articleId, Double score) {
        int status = articleMapper.selectStatus(articleId);
        if(status!=0){
            return -1;
        }
        Boolean ok = redisTemplate.opsForZSet().add(RECOMMEND_REDIS_KEY,articleId,score);
        if(Boolean.FALSE.equals(ok)){
            return -2;
        }
        return 1;
    }

    @Override
    public int deleteRecommend(String articleId) {
        Boolean ok = redisTemplate.delete(RECOMMEND_REDIS_KEY);
        if(Boolean.FALSE.equals(ok)){
            return -1;
        }
        return 1;
    }

    @Override
    public List<ArticleRecommendRes> infoRecommend() {
        List<ArticleRecommendRes> resp = new ArrayList<>();
        final Set<ZSetOperations.TypedTuple<String>> articleIds =
                redisTemplate.opsForZSet().rangeWithScores(RECOMMEND_REDIS_KEY, 0, -1);
        if (articleIds==null){
            return new ArrayList<>();
        }
        for(ZSetOperations.TypedTuple<String> tuple : articleIds){
            final InfoArticleRes infoArticleRes = articleMapper.selectDetail(tuple.getValue());
            ArticleRecommendRes articleRecommendRes = BeanUtil.copyProperties(infoArticleRes, ArticleRecommendRes.class);
            articleRecommendRes.setRecommendScore(tuple.getScore());
            resp.add(articleRecommendRes);
        }
        return resp;
    }
}
