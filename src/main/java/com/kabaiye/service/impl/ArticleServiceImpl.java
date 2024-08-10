package com.kabaiye.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kabaiye.entity.pojo.Category;
import com.kabaiye.entity.pojo.Tag;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.article.*;
import com.kabaiye.mapper.ArticleMapper;
import com.kabaiye.service.ArticleService;
import com.kabaiye.service.CategoryService;
import com.kabaiye.service.TagAtArticleService;
import com.kabaiye.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.kabaiye.entity.constant.DateConstant.DATE_FORMAT;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagAtArticleService tagAtArticleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    private static String getNextMonthFirstDay(String yearMonth) {
        // 解析传入的 yyyy-mm 格式的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth startDate = YearMonth.parse(yearMonth, formatter);
        // 计算下个月的第一天
        LocalDate nextMonthFirstDay = startDate.plusMonths(1).atDay(1);
        // 格式化为 yyyy-mm-dd 格式的字符串
        return nextMonthFirstDay.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    @Transactional
    public String saveArticle(SaveArticleBody body) {
        String id = body.getId();
        List<String> tagIds = body.getTagIds();

        // 添加分类名字段
        String categoryName = categoryService.selectNameById(body.getCategoryId());
        body.setCategoryName(categoryName);

        if (id != null) { // 更新文章
            int res = articleMapper.updateArticle(body);
            if (res <= 0) {
                throw new RuntimeException("文章更新失败");
            }
            // 删除原有标签
            for (String tagId : tagIds) {
                tagAtArticleService.delete(id, tagId);
            }
        } else { // 新增文章
            int res = articleMapper.addArticle(body);
            if (res <= 0) {
                throw new RuntimeException("文章新增失败");
            }
            // 获取返回的自增主键id
            id = body.getId();
        }
        // 添加新的文章标签
        for (String tagId : tagIds) {
            tagAtArticleService.add(id, tagId);
        }
        return id;
    }

    /**
     * 统计不同类别文章数目，"id": 1,
     * "name": "一级分类一","parentId": 0,"articleCount": 1
     */
    @Override
    @Transactional
    public List<Map<String, Object>> statisticsByCategory() {
        List<Map<String, Object>> resp = new ArrayList<>();
        // 获取所有分类
        List<Category> categoryList = categoryService.infoCategory();
        int num = 1;
        for (Category category : categoryList) {
            // 获取该分类的所有文章
            List<String> articles = articleMapper.selectByCategory(category.getId().toString());
            Map<String, Object> res = new HashMap<>();
            res.put("id", num);
            res.put("name", category.getName());
            res.put("parentId", 0);
            res.put("articleCount", articles == null ? 0 : articles.size());
            resp.add(res);
            num++;
        }
        return resp;
    }

    @Override
    public int updateStatus(String articleId, String status) {
        return articleMapper.updateStatus(articleId, status);
    }

    /**
     * 按标签统计文章数目
     * "id": 1,"name": "测试","articleCount": 3
     */
    @Override
    @Transactional
    public List<Map<String, Object>> statisticsByTag() {
        List<Map<String, Object>> resp = new ArrayList<>();
        // 获取所有标签
        List<Tag> tagList = tagService.infoTag(null);
        int num = 1;
        for (Tag tag : tagList) {
            // 获取该标签的所有文章
            List<String> articles = tagAtArticleService.selectByTagId(tag.getId());
            Map<String, Object> res = new HashMap<>();
            res.put("id", num);
            res.put("name", tag.getName());
            res.put("articleCount", articles == null ? 0 : articles.size());
            resp.add(res);
            num++;
        }
        return resp;
    }

    /**
     * 获取到已发布的文章；可用于文章标题模糊搜索；可用于文章归档年月、分类、
     * 标签查询对应文章列表；可用于点击排行列表；可用于最新文章列表。
     *
     * @param pageNum    当前页码
     * @param pageSize   每页数量
     * @param categoryId 分类id
     * @param tagId      标签id
     * @param yearMonth  yyyy-mm 日期字符串
     * @param orderBy    发布时间（默认）:publish_time、浏览数:view_count
     * @param title      标题（可选的模糊查询字段）
     */
    @Override
    public PageInfoRes pageInfo(Integer pageNum, Integer pageSize, Integer categoryId,
                                String tagId, String yearMonth, String orderBy,
                                String title, Integer isFront) {
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        String nextMonth = null;
        // 获取起止日期
        if (yearMonth != null) {
            nextMonth = getNextMonthFirstDay(yearMonth);
            yearMonth = yearMonth + "-01";
        }
        // 查询数据库获得该分页数据
        List<InfoArticleRes> infoArticleResList = articleMapper.selectPageAll(
                categoryId,  yearMonth, nextMonth, tagId, orderBy, title, isFront);
        // 为每一个查询结果文章添加完整标签列表
        for (InfoArticleRes infoArticleRes : infoArticleResList) {
            String id = infoArticleRes.getId();
            // 获取该篇文章的tag列表并存入
            List<Tag> tags = tagAtArticleService.selectTagById(id);
            infoArticleRes.setTagList(tags);
        }
        PageInfo<InfoArticleRes> pageInfo = new PageInfo<>(infoArticleResList);
        log.info("infoArticleResList: {}", infoArticleResList);
        log.info("pageInfo.getList: {}", pageInfo.getList());
        // 封装返回对象
        return new PageInfoRes(
                pageInfo.getList(),
                pageInfo.getTotal(),
                pageInfo.getSize(),
                pageInfo.getPageNum(),
                true,
                pageInfo.getPages()
        );
    }

    @Override
    public int increaseViewCount(String id) {
        return articleMapper.increaseViewCount(id);
    }

    @Override
    public int deleteArticle(String id) {
        return articleMapper.deleteArticle(id);
    }

    @Override
    public PageInfoObj<ArchivesCount> archiverCountPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArchivesCount> archivesCounts = articleMapper.archiverCount();
        PageInfo<ArchivesCount> pageInfo = new PageInfo<>(archivesCounts);
        return new PageInfoObj<>(
                pageInfo.getList(),
                pageInfo.getTotal(),
                pageInfo.getSize(),
                pageInfo.getPageNum(),
                true,
                pageInfo.getPages()
        );
    }

    /**
     * 查询文章详情，封装为InfoArticleRes+categoryList
     */
    @Override
    public Map<String, Object> articleDetail(String id) {
        final InfoArticleRes infoArticleRes = selectArticleById(id);
        // 封装返回对象
        Map<String, Object> resp = BeanUtil.beanToMap(infoArticleRes);
        // 修改响应时间字段
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        resp.put("publishTime", infoArticleRes.getPublishTime().format(formatter));
        resp.put("updateTime", infoArticleRes.getUpdateTime().format(formatter));
        // 添加categoryList字段
        List<Category> categoryList = new ArrayList<>();
        Integer categoryId = infoArticleRes.getCategoryId();
        if(categoryId!=null){
            String categoryName = categoryService.selectNameById(categoryId.toString());
            categoryList.add(new Category(categoryId, categoryName, 0, 0));
        }
        resp.put("categoryList", categoryList);
        // 返回
        return resp;
    }

    /**
     * 查询文章详情，封装为InfoArticleRes+previous+next
     */
    @Override
    public Map<String, Object> articleView(String id) {
        // 获取详情数据
        final InfoArticleRes infoArticleRes = selectArticleById(id);
        // 查询上一篇和下一篇
        Integer previousId = Integer.parseInt(id) - 1;
        Integer nextId = Integer.parseInt(id) + 1;
        String previousName = articleMapper.selectTitleById(previousId);
        String nextName = articleMapper.selectTitleById(nextId);
        // 封装返回对象
        Map<String, Object> resp = BeanUtil.beanToMap(infoArticleRes);
        // 修改时间序列化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        resp.put("publishTime", infoArticleRes.getPublishTime().format(formatter));
        resp.put("updateTime", infoArticleRes.getUpdateTime().format(formatter));
        resp.put("previous", previousName == null ? null : new ArticleIdAtTitle(previousId.toString(), previousName));
        resp.put("next", nextName == null ? null : new ArticleIdAtTitle(nextId.toString(), nextName));
        return resp;
    }

    /**
     * 查询和指定id文章相关的文章，（分类或标签相同）
     */
    @Override
    public List<InfoArticleRes> interrelatedList(String articleId, Integer limit) {
        // 获取当前文章的分类和标签列表
        String categoryId = articleMapper.selectCategoryById(articleId);
        List<String> tagIds = tagAtArticleService.selectByTagId(articleId);

        // 获取相同分类的文章id列表
        List<String> interrelatedArticleIds = articleMapper.selectByCategory(categoryId);
        // 获取有相同标签的文章id列表
        List<String> sameTagArticleIds = new ArrayList<>();
        for (String tagId : tagIds) {
            List<String> res = tagAtArticleService.selectByTagId(tagId);
            if (res != null) {
                sameTagArticleIds.addAll(res);
            }
        }
        // 合并并去重
        interrelatedArticleIds.addAll(sameTagArticleIds);
        interrelatedArticleIds = interrelatedArticleIds.stream().distinct().collect(Collectors.toList());
        // 随机从interrelatedArticleIds取limit个id出来
        List<String> resArticleIds = RandomUtil.randomEleList(interrelatedArticleIds, limit);
        // 获取对应文章详情
        List<InfoArticleRes> resp = new ArrayList<>();
        for (String resArticleId : resArticleIds){
            InfoArticleRes infoArticleRes = articleMapper.selectDetail(resArticleId);
            resp.add(infoArticleRes);
        }
        return resp;
    }

    private InfoArticleRes selectArticleById(String id) {
        // 查询数据库获得数据
        InfoArticleRes infoArticleRes = articleMapper.selectDetail(id);
        // 获取该篇文章的tag列表并存入
        List<Tag> tags = tagAtArticleService.selectTagById(id);
        infoArticleRes.setTagList(tags);
        return infoArticleRes;
    }
}
