package com.kabaiye.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kabaiye.entity.pojo.ArticleComment;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.article.ArticleIdAtTitle;
import com.kabaiye.entity.vo.comment.CommentReply;
import com.kabaiye.entity.vo.comment.CommentReplyRes;
import com.kabaiye.mapper.ArticleMapper;
import com.kabaiye.mapper.CommentMapper;
import com.kabaiye.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public int addReply(Integer fromUserId, String articleId, String commentId, String content) {
        articleMapper.addComment(articleId);
        String toUserId = commentMapper.selectCommentFromUserIdById(commentId);
        return commentMapper.addReply(fromUserId,articleId,commentId,toUserId,content);
    }

    @Override
    public String selectFromUserIdById(String replyId) {
        return commentMapper.selectFromUserIdById(replyId);
    }

    @Override
    @Transactional
    public int deleteReply(String replyId) {
        String articleId =  commentMapper.selectArticleIdByReplyId(replyId);
        articleMapper.reduceComment(articleId);
        return commentMapper.deleteReply(replyId);
    }

    @Override
    @Transactional
    public int addComment(Integer fromUserId, String articleId, String content) {
        articleMapper.addComment(articleId);
        return commentMapper.addComment(fromUserId,articleId,content);
    }

    @Override
    public String selectCommentFromUserIdById(String commentId) {
        return commentMapper.selectCommentFromUserIdById(commentId);
    }

    @Override
    @Transactional
    public int deleteComment(String commentId) {
        String articleId = commentMapper.selectArticleIdByCommentId();
        articleMapper.reduceComment(articleId);
        return commentMapper.deleteComment(commentId);
    }

    @Override
    public PageInfoObj<CommentReplyRes> getMessagePage(Integer pageNum, Integer pageSize) {

        // 所有留言及其回复
        List<CommentReplyRes> commentReplyResList = new ArrayList<>();

        // 分页查询所有评论
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleComment> ArticleCommentList = commentMapper.selectAllComment();
        PageInfo<ArticleComment> pageInfo = new PageInfo<>(ArticleCommentList);
        // 遍历评论并获取对应的回复
        for (ArticleComment articleComment : pageInfo.getList()) {
            String id = articleComment.getId();
            List<CommentReply> replyList = commentMapper.selectReplyByPid(id);

            // 将当前留言的发送者作为每一条回复的回复对象ToUser
            for (CommentReply reply : replyList) {
                reply.setToUser(articleComment.getFromUser());
            }

            // 封装留言和他的回复
            CommentReplyRes res = new CommentReplyRes(
                    id,
                    articleComment.getContent(),
                    articleComment.getCommentTime(),
                    null,
                    articleComment.getFromUser(),
                    replyList
            );
            commentReplyResList.add(res);
        }

        // 封装返回结果
        return new PageInfoObj<>(
                commentReplyResList,
                pageInfo.getTotal(),
                pageInfo.getSize(),
                pageInfo.getPageNum(),
                true,
                pageInfo.getPages()
        );
    }

    /**
     * 获取前limit条最新评论
     * @param limit
     * @return
     */
    @Override
    public List<CommentReplyRes> selectLatestComment(Integer limit) {
        List<CommentReplyRes> resp = new ArrayList<>();
        // 获取前limit条评论
        PageHelper.startPage(1,limit);
        List<ArticleComment> commentReplyResList = commentMapper.selectAllComment();
        PageInfo<ArticleComment> pageInfo = new PageInfo<>(commentReplyResList);
        // 遍历每一条评论，封装一个返回对象
        for(ArticleComment articleComment:pageInfo.getList()){
            // 获取文章标题和id
            String articleId = articleComment.getArticleId();
            String articleTitle = articleMapper.selectTitleById(Integer.parseInt(articleId));
            // 封装返回对象
            CommentReplyRes commentReplyRes = new CommentReplyRes(
                    articleComment.getId(),
                    articleComment.getContent(),
                    articleComment.getCommentTime(),
                    new ArticleIdAtTitle(articleId,articleTitle),
                    articleComment.getFromUser(),
                    new ArrayList<>()
            );
            resp.add(commentReplyRes);
        }
        return resp;
    }
}

