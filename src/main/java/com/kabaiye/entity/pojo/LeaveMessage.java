package com.kabaiye.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabaiye.entity.vo.user.ArticleUser;
import lombok.Data;
import java.time.LocalDateTime;

import static com.kabaiye.entity.constant.DateConstant.DATE_FORMAT;

/**
 * 代表留言实体类。
 */
@Data
public class LeaveMessage {

    /**
     * 主键ID。
     */
    private String id;

    /**
     * 父留言ID。如果是根留言，则为null。
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pid;

    /**
     * 留言（回复）者ID。
     */
    private ArticleUser fromUser;

    /**
     * 被回复者ID。如果是根留言，则为null。
     * null时不序列化,数据库不返回该字段，手动添加
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArticleUser toUser;

    /**
     * 内容。
     */
    private String content;

    /**
     * 创建时间。
     */
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime createTime;

    // /**
    //  * 是否删除，1: 是，0: 否。
    //  */
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // private Integer isDeleted;
}
