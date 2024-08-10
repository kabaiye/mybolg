package com.kabaiye.controller;

import com.kabaiye.entity.vo.R;
import com.kabaiye.entity.vo.article.ArticleRecommendRes;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecommendController {
    @Autowired
    private RecommendService recommendService;
    /**
     * 保存到推荐
     * 请求方法：POST
     * 请求地址：/article/recommend/save
     * 请求参数：
     * articleId：文章id，必传
     * score：分数，必传
     * 需要access_token： 是
     * 需要管理员权限： 是
     * 接口说明：只能添加已发布文章；若已添加则更新缓存；分数即为排序（顺序）。
     */
    @Admin
    @PostMapping("/article/recommend/save")
    public R saveRecommend(@RequestParam("articleId") String articleId,
                           @RequestParam("score") Double score){
        int res = recommendService.saveRecommend(articleId,score);
        if(res==-1){
            return R.error("添加失败,文章未发布");
        }else if(res==-2){
            return R.error("添加失败，缓存错误");
        }
        return R.ok(null);
    }

    /**
     * 删除推荐
     * 请求方法：DELETE
     * 请求地址：/article/recommend/delete/{articleId}
     * 需要access_token： 是
     * 需要管理员权限： 是
     * 接口说明：从推荐列表中删除。
     */
    @Admin
    @DeleteMapping("/article/recommend/delete/{articleId}")
    public R deleteRecommend(@PathVariable("articleId") String articleId){
        int res =recommendService.deleteRecommend(articleId);
        if(res<=0){
            return R.error("删除失败");
        }
        return R.ok(null);
    }

    /**
     * 获取推荐列表
     * 请求方法：GET
     * 请求地址：/article/recommend/list
     * 需要access_token： 否
     * 需要管理员权限： 否
     * 接口说明：获取文章推荐列表。
     */
    @GetMapping("/article/recommend/list")
    public R infoRecommend(){
        List<ArticleRecommendRes> resp = recommendService.infoRecommend();
        return R.ok(resp);
    }
}
