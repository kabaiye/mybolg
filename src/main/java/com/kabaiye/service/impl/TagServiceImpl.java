package com.kabaiye.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kabaiye.entity.pojo.Tag;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.mapper.TagMapper;
import com.kabaiye.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public int addTag(String tagName) {
        return tagMapper.addTag(tagName);
    }

    @Override
    public int deleteTag(String id) {
        return tagMapper.deleteTag(id);
    }

    @Override
    public int updateTag(String tagId, String tagName) {
        return tagMapper.updateTag(tagId,tagName);
    }

    @Override
    public PageInfoObj<Tag> pageInfoTag(Integer pageNum, Integer pageSize, String tagName) {
        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tags = tagMapper.selectAll(tagName);
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        return new PageInfoObj<>(
                pageInfo.getList(),
                pageInfo.getTotal(),
                pageInfo.getSize(),
                pageInfo.getPageNum(),
                true,
                pageInfo.getPages()
        );
    }

    @Override
    public List<Tag> infoTag(String tagName) {
        return tagMapper.selectAll(tagName);
    }
}
