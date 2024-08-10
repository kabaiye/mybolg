package com.kabaiye.mapper;

import com.kabaiye.entity.pojo.FriendLink;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FriendMapper {
    /**
     * 保存友链
     */
    @Insert("insert into friend_link(id,name, url, icon) VALUES (#{id},#{name},#{url},#{icon})")
    int saveFriendLink(FriendLink friendLink);

    /**
     * 删除友链
     */
    @Delete("delete from friend_link where id = #{id}")
    int deleteFriendLink(String id);

    /**
     * 获取友链列表
     */
    @Select("select id, name, url, icon from friend_link")
    List<FriendLink> infoFriendLink();
}
