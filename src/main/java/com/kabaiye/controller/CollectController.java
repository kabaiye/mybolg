package com.kabaiye.controller;

import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.R;
import com.kabaiye.entity.vo.article.InfoArticleRes;
import com.kabaiye.mapper.CollectMapper;
import com.kabaiye.myInterface.Logging;
import com.kabaiye.service.CollectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class CollectController {
    @Autowired
    private CollectService collectService;

    @Autowired
    private CollectMapper collectMapper;
    /**
     * 添加收藏
     * 接口说明：不可重复收藏。
     */
    @Logging
    @PostMapping("/article/collect/add")
    public R addCollect(@RequestParam("articleId")String articleId,
                        HttpServletRequest request){
        Integer userId =(Integer) request.getAttribute("userId");
        if(collectMapper.isCollect(userId,articleId)!=null){
            return R.error("不能重复收藏");
        }
        int res = collectService.addCollect(userId,articleId);
        if(res<=0){
            return R.error("添加失败");
        }
        return R.ok(null);
    }

    /**
     * 删除收藏
     * 请求方法：DELETE
     * 请求地址：/article/collect/delete
     * 请求参数：articleId：文章id，必传
     * 需要access_token： 是
     * 需要管理员权限： 否
     */
    @Logging
    @DeleteMapping("/article/collect/delete")
    public R deleteCollect(@RequestParam("articleId")String articleId,
                           HttpServletRequest request){
        Integer userId =(Integer) request.getAttribute("userId");
        int res = collectService.deleteCollect(userId,articleId);
        if(res<=0){
            return R.error("删除失败");
        }
        return R.ok(null);
    }

    /**
     * 是否已收藏
     * 请求方法：GET
     * 请求地址：/article/collect/collected/{articleId}
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 接口说明：用于文章详情在登录情况下显示当前用户是否已对当前文章收藏；1:是，0:否。
     * 获取成功:
     */
    @Logging
    @GetMapping("/article/collect/collected/{articleId}")
    public R isCollected(@PathVariable("articleId")String articleId,
                         HttpServletRequest request){
        Integer userId =(Integer) request.getAttribute("userId");
        if(collectMapper.isCollect(userId,articleId)==null){
            return R.ok(0);
        }
        return R.ok(1);
    }

    /**
     * 分页获取收藏
     * 请求方法：GET
     * 请求地址：/article/collect/page
     * 请求参数：
     * current：当前页，非必传，默认1
     * size：每页数量，非必传，默认5
     * 需要access_token： 是
     * 需要管理员权限： 否
     */
    @Logging
    @GetMapping("/article/collect/page")
    public R collectPage(@RequestParam(value = "current", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
                         HttpServletRequest request){
        log.info("访问/article/collect/page");
        Integer userId =(Integer) request.getAttribute("userId");
        PageInfoObj<InfoArticleRes> resp = collectService.collectPage(pageNum,pageSize,userId);
        log.info("/article/collect/page结束，返回：{}",resp);
        return R.ok(resp);
    }
}
