package com.kabaiye.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.article.InfoArticleRes;
import com.kabaiye.mapper.ArticleMapper;
import com.kabaiye.mapper.CollectMapper;
import com.kabaiye.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    @Transactional
    public int addCollect(Integer userId, String articleId) {
        articleMapper.addCollect(articleId);
        return collectMapper.addCollect(userId,articleId);
    }

    @Override
    @Transactional
    public int deleteCollect(Integer userId, String articleId) {
        articleMapper.reduceCollect(articleId);
        return collectMapper.deleteCollect(userId,articleId);
    }

    @Override
    public PageInfoObj<InfoArticleRes> collectPage(Integer pageNum, Integer pageSize, Integer userId) {
        // 分页获取该页用户id
        PageHelper.startPage(pageNum,pageSize);
        List<String> collectArticleIds = collectMapper.selectArticleIdByUserId(userId);
        PageInfo<String> pageInfo = new PageInfo<>(collectArticleIds);
        if(pageInfo.getList().isEmpty()){
            return new PageInfoObj<>(
                    new ArrayList<>(),
                    pageInfo.getTotal(),
                    pageInfo.getSize(),
                    pageInfo.getPageNum(),
                    true,
                    pageInfo.getPages()
            );
        }
        List<InfoArticleRes> articles = articleMapper.selectDetailAll(pageInfo.getList());
        return new PageInfoObj<>(
                articles,
                pageInfo.getTotal(),
                pageInfo.getSize(),
                pageInfo.getPageNum(),
                true,
                pageInfo.getPages()
        );
    }
}
