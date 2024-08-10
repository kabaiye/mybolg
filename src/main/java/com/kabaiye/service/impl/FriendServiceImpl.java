package com.kabaiye.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kabaiye.entity.pojo.FriendLink;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.mapper.FriendMapper;
import com.kabaiye.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;
    @Override
    public int saveFriendLink(FriendLink friendLink) {
        return friendMapper.saveFriendLink(friendLink);
    }

    @Override
    public int deleteFriendLink(String id) {
        return friendMapper.deleteFriendLink(id);
    }

    @Override
    public List<FriendLink> infoFriendLink() {
        return friendMapper.infoFriendLink();
    }

    @Override
    public PageInfoObj<FriendLink> pageInfoFriendLink(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<FriendLink> friendLinks = friendMapper.infoFriendLink();
        PageInfo<FriendLink> pageInfo =new PageInfo<>(friendLinks);
        return new PageInfoObj<>(
                pageInfo.getList(),
                pageInfo.getTotal(),
                pageInfo.getPageSize(),
                pageInfo.getPageNum(),
                true,
                pageInfo.getPages()
        );
    }
}
