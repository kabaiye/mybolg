package com.kabaiye.service;

import com.kabaiye.entity.pojo.Tag;
import com.kabaiye.entity.vo.PageInfoObj;

import java.util.List;

public interface TagService {
    /**
     * 添加标签
     */
    int addTag(String tagName);

    /**
     * 逻辑删除标签
     */
    int deleteTag(String id);

    /**
     * 修改标签名
     */
    int updateTag(String tagId, String tagName);

    /**
     * 分页动态模糊查询
     * @return
     */
    PageInfoObj<Tag> pageInfoTag(Integer pageNum, Integer pageSize, String tagName);

    /**
     * 不分页模糊查询
     */
    List<Tag> infoTag(String tagName);
}
