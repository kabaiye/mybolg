package com.kabaiye.service;

import com.kabaiye.entity.pojo.FriendLink;
import com.kabaiye.entity.vo.PageInfoObj;

import java.util.List;

public interface FriendService {
    /**
     * 保存友链
     */
    int saveFriendLink(FriendLink friendLink);
    /**
     * 删除友链
     */
    int deleteFriendLink(String id);

    /**
     * 获取友链列表
     */
    List<FriendLink> infoFriendLink();

    /**
     * 分页获取友链列表
     */
    PageInfoObj<FriendLink> pageInfoFriendLink(Integer pageNum, Integer pageSize);
}
