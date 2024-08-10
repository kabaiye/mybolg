package com.kabaiye.mapper;

import com.kabaiye.entity.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagAtArticleMapper {

    /**
     * 删除标签与文章的关联关系。
     *
     * @param tagId 标签ID
     * @param articleId 文章ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM article_tag WHERE tag_id = #{tagId} AND article_id = #{articleId}")
    int delete(@Param("tagId") String tagId, @Param("articleId") String articleId);

    /**
     * 添加标签与文章的关联关系。
     *
     * @param tagId 标签ID
     * @param articleId 文章ID
     * @return 影响的行数
     */
    @Insert("INSERT INTO article_tag (article_id, tag_id) VALUES (#{articleId}, #{tagId})")
    int add(@Param("tagId") String tagId, @Param("articleId") String articleId);

    /**
     * 根据标签ID查询关联的文章列表。
     *
     * @param tagId 标签ID
     * @return 关联的文章列表
     */
    @Select("SELECT article_id FROM article_tag  WHERE tag_id = #{tagId}")
    List<String> selectByTagId(@Param("tagId") String tagId);

    /**
     * 根据文章ID查询关联的标签列表。
     *
     * @param articleId 文章ID
     * @return 关联的标签列表
     */
    @Select("SELECT tag_id FROM article_tag WHERE article_id = #{articleId}")
    List<String> selectByArticleId(@Param("articleId") String articleId);

    /**
     * 根据文章id查询文章tag列表
     */
    @Select("select t.id, t.name " +
            "from article_tag at " +
            "join tag t on at.tag_id = t.id " +
            "where at.article_id = #{id}")
    List<Tag> selectTagById(String id);
}
