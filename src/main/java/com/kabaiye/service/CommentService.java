package com.kabaiye.service;

import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.comment.CommentReplyRes;

import java.util.List;

public interface CommentService {
    /**
     * 新增评论回复
     */
    int addReply(Integer fromUserId, String articleId, String commentId,  String content);

    String selectFromUserIdById(String replyId);

    /**
     * 删除评论回复
     */
    int deleteReply(String replyId);

    /**
     * 新增评论
     */
    int addComment(Integer fromUserId, String articleId, String content);

    String selectCommentFromUserIdById(String commentId);

    /**
     * 删除评论
     */
    int deleteComment(String commentId);

    /**
     * 分页获取“评论及其回复”的列表
     */
    PageInfoObj<CommentReplyRes> getMessagePage(Integer pageNum, Integer pageSize);

    /**
     * 获取最新limit条评论
     */
    List<CommentReplyRes> selectLatestComment(Integer limit);
}
