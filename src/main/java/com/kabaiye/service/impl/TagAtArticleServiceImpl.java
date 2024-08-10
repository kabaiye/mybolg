package com.kabaiye.service.impl;

import com.kabaiye.entity.pojo.Tag;
import com.kabaiye.service.TagAtArticleService;


import com.kabaiye.mapper.TagAtArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagAtArticleServiceImpl implements TagAtArticleService {

    @Autowired
    private TagAtArticleMapper tagAtArticleMapper;

    @Override
    @Transactional
    public int delete(String tagId, String articleId) {
        // 调用 TagAtArticleMapper 中的 delete 方法
        return tagAtArticleMapper.delete(tagId, articleId);
    }

    @Override
    @Transactional
    public int add(String tagId, String articleId) {
        // 调用 TagAtArticleMapper 中的 add 方法
        return tagAtArticleMapper.add(tagId, articleId);
    }

    @Override
    public List<String> selectByTagId(String tagId) {
        // 调用 TagAtArticleMapper 中的 selectByTagId 方法
        return tagAtArticleMapper.selectByTagId(tagId);
    }

    @Override
    public List<String> selectByArticleId(String articleId) {
        // 调用 TagAtArticleMapper 中的 selectByArticleId 方法
        return tagAtArticleMapper.selectByArticleId(articleId);
    }

    @Override
    public List<Tag> selectTagById(String id) {
        return tagAtArticleMapper.selectTagById(id);
    }
}
