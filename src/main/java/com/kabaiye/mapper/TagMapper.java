package com.kabaiye.mapper;

import com.kabaiye.entity.pojo.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagMapper {
    /**
     * 添加标签
     */
    @Insert("insert into tag(name) values (#{tagName})")
    int addTag(@Param("tagName") String tagName);

    /**
     * 逻辑删除标签
     */
    @Update("update tag set deleted=1 where id=#{tagId}")
    int deleteTag(@Param("tagId") String tagId);

    /**
     * 修改标签名
     */
    @Update("update tag set name=#{tagName} where id=#{tagId} and deleted=0")
    int updateTag(@Param("tagId") String tagId,@Param("tagName") String tagName);

    /**
     * 动态模糊查询
     */
    List<Tag> selectAll(@Param("tagName") String tagName);

    /**
     * 按标签名查找标签
     */
    @Select("select id,name from tag where name=#{name} and deleted=0")
    Tag selectTagByName(@Param("name") String tagName);
}
