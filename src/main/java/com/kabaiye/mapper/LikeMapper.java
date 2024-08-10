package com.kabaiye.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface LikeMapper {
    /**
     * 新增文章点赞
     */
    @Insert("insert into article_like(article_id, user_id) VALUES (#{articleId},#{userId})")
    int addLike(@Param("userId") Integer userId,@Param("articleId") String articleId);

    /**
     * 取消点赞
     */
    @Delete("delete from article_like where user_id=#{userId} and article_id=#{articleId}")
    int cancelLike(@Param("userId") Integer userId,@Param("articleId") String articleId);

    /**
     * 是否点赞
     * @return 返回条目个数
     */
    @Select("select count(*) res from article_like where user_id=#{userId} and article_id=#{articleId}")
    int isLiked(@Param("userId") Integer userId,@Param("articleId") String articleId);
}
