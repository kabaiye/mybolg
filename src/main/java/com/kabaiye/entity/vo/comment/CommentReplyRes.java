package com.kabaiye.entity.vo.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kabaiye.entity.pojo.ArticleComment;
import com.kabaiye.entity.pojo.LeaveMessage;
import com.kabaiye.entity.vo.article.ArticleIdAtTitle;
import com.kabaiye.entity.vo.user.ArticleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.kabaiye.entity.constant.DateConstant.DATE_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReplyRes {
    /**
     * 评论id
     */
    private String id;

    /**
     * 内容。
     */
    private String content;

    /**
     * 评论时间。
     * 格式为yyyy-MM-dd
     */
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime commentTime;

    /**
     * 文章简要信息
     */
    private ArticleIdAtTitle article;

    /**
     * 评论（回复）者。
     */
    private ArticleUser fromUser;


    /**
     * 回复列表 toUser == this.fromUser
     */
    private List<CommentReply> replyList;
}
