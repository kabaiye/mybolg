package com.kabaiye.controller;

import com.kabaiye.entity.vo.R;
import com.kabaiye.myInterface.Logging;
import com.kabaiye.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;
    /**
     * 新增点赞
     * 请求方法：POST
     * 请求地址：/article/like/add
     * 请求参数：articleId：文章id，必传
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 接口说明：不可重复点赞。
     */
    @Logging
    @PostMapping("/article/like/add")
    public R addLike(@RequestParam("articleId")String articleId,
                     HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        int res = likeService.addLike(userId,articleId);
        if(res<=0){
            return R.error("点赞失败");
        }
        return R.ok(null);
    }
    /**
     * 取消点赞
     * 请求方法：DELETE
     * 请求地址：/article/like/cancel
     * 请求参数：articleId：文章id，必传
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 接口说明：对已点赞文章取消点赞。
     */
    @Logging
    @DeleteMapping("/article/like/cancel")
    public R cancelLike(@RequestParam("articleId")String articleId,
                        HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        int res = likeService.cancelLike(userId,articleId);
        if(res<=0){
            return R.error("取消点赞失败");
        }
        return R.ok(null);
    }

    /**
     * 是否已点赞
     * 请求方法：GET
     * 请求地址：/article/like/liked/{articleId}
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 接口说明：用于文章详情在登录情况下显示当前用户是否已对当前文章点赞；1:是，0:否。
     * 获取成功:
     */
    @Logging
    @GetMapping("/article/like/liked/{articleId}")
    public R isLiked(@PathVariable("articleId")String articleId,
                     HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        int res = likeService.isLiked(userId,articleId);
        if(res==0){
            return R.ok(0);
        }
        return R.ok(1);
    }
}
