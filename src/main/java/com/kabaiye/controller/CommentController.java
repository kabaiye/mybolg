package com.kabaiye.controller;

import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.R;
import com.kabaiye.entity.vo.comment.CommentReplyRes;
import com.kabaiye.myInterface.Logging;
import com.kabaiye.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 新增评论回复
     * 请求方法：POST
     * 请求地址：/article/reply/add
     * 请求参数：
     * articleId：文章id，必传
     * commentId：评论id，必传
     * toUserId：被回复者id，必传
     * content：回复内容，必传
     * 需要access_token： 是
     * 需要管理员权限： 否
     */
    @Logging
    @PostMapping("/article/reply/add")
    public R addReply(
            @RequestParam("articleId") String articleId,
            @RequestParam("commentId") String commentId,
            @RequestParam("content") String content,
            HttpServletRequest request) {
        Integer fromUserId = (Integer) request.getAttribute("userId");
        int res = commentService.addReply(fromUserId, articleId, commentId, content);
        if (res <= 0) {
            return R.error("回复失败");
        }
        return R.ok(null);
    }

    /**
     * 删除评论回复
     * 请求方法：DELETE
     * 请求地址：/article/reply/delete
     * 请求参数：replyId：回复id，必传
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 接口说明：逻辑删除；本人或管理员可删除。
     */
    @Logging
    @DeleteMapping("/article/reply/delete")
    public R deleteReply(@RequestParam("replyId") String replyId,
                         HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        Integer admin = (Integer) request.getAttribute("admin");
        String fromUserId = commentService.selectFromUserIdById(replyId);
        if (admin != 1 && userId.toString().equals(fromUserId)) {
            return R.error("无权删除");
        }
        int res = commentService.deleteReply(replyId);
        if (res <= 0) {
            return R.error("删除失败");
        }
        return R.ok(null);
    }

    /**
     * 新增评论
     * 请求方法：POST
     * 请求地址：/article/comment/add
     * 请求参数：
     * articleId：文章id，必传
     * content：评论内容，必传
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 接口说明：评论会给文章作者发送评论提醒邮件。
     * 评论成功:
     */
    @Logging
    @PostMapping("/article/comment/add")
    public R addComment(@RequestParam("articleId") String articleId,
                        @RequestParam("content") String content,
                        HttpServletRequest request) {
        Integer fromUserId = (Integer) request.getAttribute("userId");
        int res = commentService.addComment(fromUserId, articleId, content);
        if (res <= 0) {
            return R.error("评论失败");
        }
        return R.ok(null);
    }

    /**
     * 删除评论
     * 请求方法：DELETE
     * 请求地址：/article/comment/delete
     * 请求参数：commentId：评论id，必传
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 接口说明：逻辑删除；本人或系统管理员可删除。
     * 删除成功:
     */
    @Logging
    @DeleteMapping("/article/comment/delete")
    public R deleteComment(@RequestParam("commentId") String commentId,
                           HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        Integer admin = (Integer) request.getAttribute("admin");
        if (admin != 1 && userId.toString().equals(commentService.selectCommentFromUserIdById(commentId))) {
            return R.error("无权删除");
        }
        int res = commentService.deleteComment(commentId);
        if (res <= 0) {
            return R.error("删除失败");
        }
        return R.ok(null);
    }

    /**
     * 分页获取评论与回复
     * 请求方法：GET
     * 请求地址：/article/comment/page
     * 请求参数：
     * current：当前页，非必传，默认1
     * size：每页数量，非必传，默认5
     * articleId：文章id，必传
     * 需要access_token： 否
     * 需要管理员权限： 否
     * 接口说明：评论下挂回复列表；按时间降序排序。
     */
    @GetMapping("/article/comment/page")
    public R pageInfoComment(@RequestParam(value = "current", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
                             @RequestParam("articleId") String articleId) {
        PageInfoObj<CommentReplyRes> resp = commentService.getMessagePage(pageNum, pageSize);
        return R.ok(resp);
    }
    /**
     * 最新评论列表
     * 请求方法：GET
     * 请求地址：/article/comment/latest
     * 请求参数：limit：数量，非必传，默认5
     * 需要access_token： 否
     * 需要管理员权限： 否
     */
    @GetMapping("/article/comment/latest")
    public R latestComment(@RequestParam(value = "limit", defaultValue = "") Integer limit){
        List<CommentReplyRes> resp = commentService.selectLatestComment(limit);
        return R.ok(resp);
    }
}
