package com.kabaiye.service;
import com.kabaiye.entity.pojo.Tag;

import java.util.List;

public interface TagAtArticleService {

    /**
     * 删除标签与文章的关联关系。
     *
     * @param tagId 标签ID
     * @param articleId 文章ID
     * @return 影响的行数
     */
    int delete(String tagId, String articleId);

    /**
     * 添加标签与文章的关联关系。
     *
     * @param tagId 标签ID
     * @param articleId 文章ID
     * @return 影响的行数
     */
    int add(String tagId, String articleId);

    /**
     * 根据标签ID查询关联的文章列表。
     *
     * @param tagId 标签ID
     * @return 关联的文章列表
     */
    List<String> selectByTagId(String tagId);

    /**
     * 根据文章ID查询关联的标签列表。
     *
     * @param articleId 文章ID
     * @return 关联的标签列表
     */
    List<String> selectByArticleId(String articleId);

    /**
     * 按文章id返回tag列表
     * @param id
     * @return
     */
    List<Tag> selectTagById(String id);
}

