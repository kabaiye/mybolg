package com.kabaiye.service;

import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.article.ArchivesCount;
import com.kabaiye.entity.vo.article.InfoArticleRes;
import com.kabaiye.entity.vo.article.PageInfoRes;
import com.kabaiye.entity.vo.article.SaveArticleBody;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    /**
     * 保存文章，id==null为新增，否则为更新，设置为待发布
     * @return 文章id
     */
    String saveArticle(SaveArticleBody body);

    /**
     * 统计不同分类的文章个数
     * @return
     */
    List<Map<String, Object>> statisticsByCategory();

    /**
     * 修改文章状态
     */
    int updateStatus(String articleId, String status);

    /**
     * 按标签统计文章数目
     */
    List<Map<String, Object>> statisticsByTag();

    /**
     * 获取到已发布的文章；可用于文章标题模糊搜索；可用于文章归档年月、分类、
     * 标签查询对应文章列表；可用于点击排行列表；可用于最新文章列表。
     * @param pageNum 当前页码
     * @param pageSize 每页数量
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param yearMonth yyyy-mm 日期字符串
     * @param orderBy 发布时间（默认）:publish_time、浏览数:view_count
     * @param title 标题（可选的模糊查询字段）
     * @param isFront 是否是前台调用，1：返回已经发布的，0：返回未删除的
     * @return
     */
    PageInfoRes pageInfo(Integer pageNum, Integer pageSize, Integer categoryId,
                         String tagId, String yearMonth, String orderBy,
                         String title, Integer isFront);


    /**
     * 增加浏览量+1
     */
    int increaseViewCount(String id);

    /**
     * 逻辑删除
     */
    int deleteArticle(String id);

    /**
     * 分页统计年月文章数
     */
    PageInfoObj<ArchivesCount> archiverCountPage(Integer pageNum, Integer pageSize);

    /**
     * 查询文章详情，封装为InfoArticleRes+categoryList
     */
    Map<String, Object> articleDetail(String id);

    /**
     * 查询文章详情，封装为InfoArticleRes+previous+next
     */
    Map<String, Object> articleView(String id);

    /**
     * 查询和指定id文章相关的文章，（分类或标签相同）
     */
    List<InfoArticleRes> interrelatedList(String articleId, Integer limit);
}
