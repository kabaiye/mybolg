package com.kabaiye.service;

import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.article.InfoArticleRes;

public interface CollectService {
    /**
     * 添加收藏
     */
    int addCollect(Integer userId, String articleId);

    /**
     * 删除收藏
     */
    int deleteCollect(Integer userId, String articleId);

    /**
     * 分页获取已经收藏的文章
     */
    PageInfoObj<InfoArticleRes> collectPage(Integer pageNum, Integer pageSize, Integer userId);
}
