package com.kabaiye.mapper;

import com.kabaiye.entity.vo.article.ArchivesCount;
import com.kabaiye.entity.vo.article.InfoArticleRes;
import com.kabaiye.entity.vo.article.SaveArticleBody;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper {
    /**
     * 更新文章，设置为待发布状态(status=1)
     * 需要返回自增主键
     */
    int updateArticle(SaveArticleBody body);

    /**
     * 新增文章，设置为待发布状态(status=1)
     * 需要返回自增主键
     */
    int addArticle(SaveArticleBody body);

    /**
     * 按分类查询文章id列表
     */
    @Select("SELECT id FROM article WHERE category_id = #{categoryId} AND deleted = 0")
    List<String> selectByCategory(String categoryId);

    /**
     * 修改文章状态
     */
    @Update("UPDATE article SET status = #{status} WHERE id = #{articleId}")
    int updateStatus(@Param("articleId") String articleId,@Param("status") String status);

    /**
     * 查询文章列表 *为可选的筛选条件
     * @param categoryId 分类id*
     * @param tagId 标签id*
     * @param yearMonth 起始月*
     * @param nextMonth 终止月*
     * @param orderBy 排序依据
     * @param title 标题*
     * @param isFront 是否是前台调用 1：前台调用，返回已发布，0：后台调用，返回未删除
     * @return InfoArticleRes
     */
    List<InfoArticleRes> selectPageAll(@Param("categoryId") Integer categoryId, @Param("yearMonth") String yearMonth,
                                       @Param("nextMonth") String nextMonth, @Param("tagId") String tagId,
                                       @Param("orderBy") String orderBy, @Param("title") String title,
                                       @Param("isFront") Integer isFront);


    /**
     * 浏览量view_count +1
     */
    @Update("UPDATE article SET view_count = view_count + 1 WHERE id = #{id}")
    int increaseViewCount(@Param("id") String id);

    /**
     * 逻辑删除，deleted=1
     */
    @Update("UPDATE article SET deleted = 1 WHERE id = #{id}")
    int deleteArticle(@Param("id") String id);

    /**
     * 按年月分组，统计文章个数
     */
    @Select("select date_format(publish_time,'%Y-%m') as yearMonth, " +
            "       count(*) as articleCount   " +
            "from article " +
            "group by date_format(publish_time,'%Y-%m') " +
            "order by yearMonth")
    List<ArchivesCount> archiverCount();

    /**
     * 获取文章详情
     */
    InfoArticleRes selectDetail(@Param("id") String id);

    /**
     * 查找文章标题
     */
    @Select("SELECT title FROM article WHERE id = #{id}")
    String selectTitleById(@Param("id") Integer id);

    /**
     * 查询文章分类id
     */
    @Select("SELECT category_id FROM article WHERE id = #{articleId}")
    String selectCategoryById(@Param("articleId") String articleId);

    /**
     * 批量获取文章详情
     */
    List<InfoArticleRes> selectDetailAll(@Param("articleIds") List<String> articleIds);

    /**
     * 增加点赞
     */
    @Update("UPDATE article SET like_count = like_count + 1 WHERE id = #{articleId}")
    void addLike(String articleId);

    /**
     * 减少点赞
     */
    @Update("UPDATE article SET like_count = like_count - 1 WHERE id = #{articleId}")
    void reduceLike(String articleId);

    /**
     * 增加收藏
     */
    @Update("UPDATE article SET collect_count = collect_count + 1 WHERE id = #{articleId}")
    void addCollect(String articleId);

    /**
     * 减少收藏
     */
    @Update("UPDATE article SET collect_count = collect_count - 1 WHERE id = #{articleId}")
    void reduceCollect(String articleId);

    /**
     * 增加评论
     */
    @Update("UPDATE article SET comment_count = comment_count + 1 WHERE id = #{articleId}")
    void addComment(String articleId);

    /**
     * 减少评论
     */
    @Update("UPDATE article SET comment_count = comment_count - 1 WHERE id = #{articleId}")
    void reduceComment(String articleId);

    /**
     * 查询状态
     */
    @Select("SELECT status FROM article WHERE id = #{articleId}")
    int selectStatus(String articleId);
}
