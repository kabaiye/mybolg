package com.kabaiye.controller;

import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.R;
import com.kabaiye.entity.vo.article.ArchivesCount;
import com.kabaiye.entity.vo.article.InfoArticleRes;
import com.kabaiye.entity.vo.article.PageInfoRes;
import com.kabaiye.entity.vo.article.SaveArticleBody;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 保存文章
     * 文章id为null时为新增，不为null时为更新；文章标签最多4个；
     * 文章为转载时，需标出转载地址；接口将文章状态置为待发布状态status=1;保存成功返回文章id；
     */
    @PostMapping("/article/save")
    @Admin
    public R saveArticle(@RequestBody SaveArticleBody body,
                         HttpServletRequest request){
        Integer userId =(Integer) request.getAttribute("userId");
        body.setUserId(userId);
        if(body.getTagIds()!=null && body.getTagIds().size()>4){
            log.info("body.getTagIds():{}",body.getTagIds());
            return R.error("标签数量不得超过4个");
        }
        String id = articleService.saveArticle(body);
        if(id==null){
            return R.error("保存文章失败");
        }
        return R.ok(id);
    }

    /**
     * 按分类统计文章数。
     */
    @GetMapping("/article/category/statistic")
    public R statisticsByCategory(){
        List<Map<String,Object>> resp = articleService.statisticsByCategory();
        return R.ok(resp);
    }

    /**
     * 修改文章状态 0:发布，1:待发布，2:回收站
     * 接口说明：用于发布文章撤回，删除文章撤回等操作。
     */
    @Admin
    @PostMapping("/article/status/update")
    public R updateStatus(@RequestParam("articleId") String articleId,
                          @RequestParam("status") String status){
        int res = articleService.updateStatus(articleId,status);
        if(res<=0){
            R.error("修改文章状态失败");
        }
        return R.ok(null);
    }

    /**
     * 按标签统计文章数
     */
    @GetMapping("/article/tag/statistic")
    public R statisticsByTag(){
        List<Map<String,Object>> resp = articleService.statisticsByTag();
        return R.ok(resp);
    }

    /**
     * 分页获取已发布文章（前台）
     * current：当前页，非必传，默认1
     * size：每页数量，非必传，默认5
     * categoryId：分类id，非必传
     * tagId：标签id，非必传
     * yearMonth：年月,格式yyyy-mm，非必传
     * orderBy：排序字段，倒序，非必传，默认:publish_time;可选项：发布时间:publish_time、浏览数:view_count
     * title：标题关键字，非必传
     * 接口说明：只可获取到已发布的文章；可用于文章标题模糊搜索；可用于文章归档年月、分类、
     * 标签查询对应文章列表；可用于点击排行列表；可用于最新文章列表。
     */
    @GetMapping("/article/published/page")
    public R pageInfoFront(
            @RequestParam(value = "current", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "tagId", required = false) String tagId,
            @RequestParam(value = "yearMonth", required = false) String yearMonth, // yyyy-mm
            //排序字段，倒序，可选项：发布时间:publish_time、浏览数:view_count
            @RequestParam(value = "orderBy", defaultValue = "publish_time") String orderBy,
            @RequestParam(value = "title", required = false) String title) {
        // 业务逻辑处理
        PageInfoRes pageInfoRes = articleService.pageInfo(pageNum, pageSize, categoryId,
                tagId, yearMonth, orderBy, title, 1);
        // 返回结果
        return R.ok(pageInfoRes);
    }


    /**
     *
     * 和/published/page 一样，但是是获取到所有未删除的文章
     */
    @GetMapping("/article/page")
    public R pageInfo(
            @RequestParam(value = "current", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "tagId", required = false) String tagId,
            @RequestParam(value = "yearMonth", required = false) String yearMonth, // yyyy-mm
            //排序字段，倒序，可选项：发布时间:publish_time、浏览数:view_count
            @RequestParam(value = "orderBy", defaultValue = "publish_time") String orderBy,
            @RequestParam(value = "title", required = false) String title) {
        // 业务逻辑处理
        PageInfoRes result = articleService.pageInfo(pageNum, pageSize, categoryId,
                tagId, yearMonth, orderBy, title, 0);
        // 返回结果
        return R.ok(result);
    }

    /**
     * 文章浏览数自增
     * 请求方法：PUT
     * 请求地址：/increment_view/{id}
     * 需要access_token： 否
     * 需要管理员权限： 否
     * 接口说明：20分钟内ip或用户文章浏览计数，data返回true则表示此次成功自增
     * 调用成功：
     */
    @PutMapping("/article/increment_view/{id}")
    public R increaseViewCount(@PathVariable("id") String id){
        int res = articleService.increaseViewCount(id);
        if(res<=0){
            return R.error("浏览量增加失败");
        }
        return R.ok(true);
    }

    /**
     * 丢弃文章
     * 接口说明：文章状态将置为回收站状态。
     */
    @Admin
    @DeleteMapping("/article/discard/{id}")
    public R discardArticle(@PathVariable("id") String id){
        int res = articleService.updateStatus(id, "2");
        if(res<=0){
            return R.error("删除文章失败");
        }
        return R.ok(null);
    }

    /**
     * 请求方法：DELETE
     * 请求地址：/article/delete/{id}
     * 需要access_token： 是
     * 需要管理员权限： 是
     * 接口说明：逻辑删除。
     */
    @Admin
    @DeleteMapping("/article/delete/{id}")
    public R deleteArticle(@PathVariable("id") String id){
        int res = articleService.deleteArticle(id);
        if(res<=0){
            return R.error("删除文章失败");
        }
        return R.ok(null);
    }


    /**
     * 文章归档,统计年月的文章数量
     * 接口说明：按年月归档，月份文章计数。
     */
    @GetMapping("/article/archives/page")
    public R archivesPage(@RequestParam(value = "current", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "size", defaultValue = "12") Integer pageSize){
        PageInfoObj<ArchivesCount> resp = articleService.archiverCountPage(pageNum,pageSize);
        return R.ok(resp);
    }

    /**
     * 文章详情（后台），包括一个分类树列表
     */
    @Admin
    @GetMapping("/article/detail/{id}")
    public R articleDetail(@PathVariable("id") String id){
        Map<String,Object> resp = articleService.articleDetail(id);
        return R.ok(resp);
    }

    /**
     * 文章详情（前台），包括上一篇和下一篇
     */
    @GetMapping("/article/view/{id}")
    public R articleView(@PathVariable("id") String id){
        Map<String,Object> resp = articleService.articleView(id);
        return R.ok(resp);
    }

    /**
     * 相关文章
     * 请求方法：GET
     * 请求地址：/article/interrelated/list
     * 请求参数：
     * articleId：文章id，必传
     * limit：列表数量，非必传，默认5
     * 需要access_token： 否
     * 需要管理员权限： 否
     * 接口说明：后台实际根据分类或标签查询。
     * 获取成功：
     */
    @GetMapping("/article/interrelated/list")
    public R interrelatedList(@RequestParam("articleId") String articleId,
                              @RequestParam(value = "limit", defaultValue = "5") Integer limit){
        List<InfoArticleRes> resp = articleService.interrelatedList(articleId,limit);
        return R.ok(resp);
    }
}
