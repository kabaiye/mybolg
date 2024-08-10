package com.kabaiye.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectMapper {
    /**
     * 添加收藏
     */
    @Insert("insert into article_collect(user_id, article_id) values(#{userId},#{articleId})")
    int addCollect(@Param("userId") Integer userId,@Param("articleId") String articleId);

    /**
     * 是否已经收藏
     */
    @Select("select id from article_collect where user_id=#{userId} and article_id=#{articleId}")
    String isCollect(@Param("userId") Integer userId,@Param("articleId") String articleId);

    /**
     * 删除收藏
     */
    @Delete("delete from article_collect where user_id=#{userId} and article_id=#{articleId}")
    int deleteCollect(@Param("userId") Integer userId,@Param("articleId") String articleId);

    /**
     * 按userId查询收藏的文章
     */
    @Select("select article_id from article_collect where user_id=#{userId}")
    List<String> selectArticleIdByUserId(Integer userId);
}
