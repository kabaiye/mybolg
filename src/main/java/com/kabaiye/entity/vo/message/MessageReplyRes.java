package com.kabaiye.entity.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabaiye.entity.pojo.LeaveMessage;
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
public class MessageReplyRes {
    /**
     * 留言id
     */
    private String id;

    /**
     * 内容。
     */
    private String content;

    /**
     * 创建时间。
     * 格式为yyyy-MM-dd
     */
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime createTime;

    /**
     * 留言（回复）者。
     */
    private ArticleUser fromUser;


    /**
     * 回复列表 toUser == this.fromUser
     */
    private List<LeaveMessage> replyList;
}
