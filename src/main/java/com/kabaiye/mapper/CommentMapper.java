package com.kabaiye.mapper;

import com.kabaiye.entity.pojo.ArticleComment;
import com.kabaiye.entity.vo.comment.CommentReply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    /**
     * 新增评论回复
     */
    @Insert("insert into article_reply(article_id, comment_id, from_user_id, to_user_id, content) " +
            " VALUES(#{articleId},#{commentId},#{fromUserId},#{toUserId},#{content})")
    int addReply(Integer fromUserId, String articleId, String commentId, String toUserId, String content);

    /**
     * 查询回复发出者id
     */
    @Select("select from_user_id from article_reply where id = #{replyId}")
    String selectFromUserIdById(String replyId);

    /**
     * 删除评论回复
     */
    @Update("update article_reply set deleted = 1 where id = #{replyId}")
    int deleteReply(String replyId);

    /**
     * 新增评论
     */
    @Insert("insert into article_comment(article_id, from_user_id, content) values " +
            "(#{articleId},#{fromUserId},#{content})")
    int addComment(Integer fromUserId, String articleId, String content);

    /**
     * 查询评论发出者id
     */
    @Select("select from_user_id from article_comment where id = #{commentId}")
    String selectCommentFromUserIdById(String commentId);

    /**
     * 删除评论
     */
    @Update("update article_comment set deleted = 1 where id = #{commentId}")
    int deleteComment(String commentId);


    /**
     * 查询所有评论,按时间降序
     */
    List<ArticleComment> selectAllComment();

    /**
     *获取对应pid的回复列表，按时间降序
     */
    List<CommentReply> selectReplyByPid(String pid);

    /**
     * 根据回复评论id，查询文章id
     */
    @Select("select article_id from article_reply where id = #{replyId}")
    String selectArticleIdByReplyId(String replyId);

    /**
     * 根据评论id查询文章id
     */
    @Select("select article_id from article_comment where id = #{commentId}")
    String selectArticleIdByCommentId();
}
