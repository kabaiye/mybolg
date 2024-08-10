package com.kabaiye.entity.vo.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kabaiye.entity.constant.DateConstant;
import com.kabaiye.entity.vo.user.ArticleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReply {
    /**
     * 回复id
     */
    private String id;

    /**
     * 回复的内容
     */
    private String content;

    @JsonFormat(pattern = DateConstant.DATE_FORMAT)
    private LocalDateTime replyTime;

    /**
     * 谁发的
     */
    private ArticleUser fromUser;

    // 不从数据库获取
    private ArticleUser toUser;

}
