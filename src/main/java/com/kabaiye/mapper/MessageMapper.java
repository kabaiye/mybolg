package com.kabaiye.mapper;

import com.kabaiye.entity.pojo.LeaveMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    /**
     * 新增留言
     */
    @Insert("insert into leave_message(from_user_id,content) values(#{userId},#{content})")
    int addMessage(@Param("userId") Integer userId, @Param("content") String content);

    /**
     * 逻辑删除留言
     */
    @Update("update leave_message set deleted=1 where id = #{msgId}")
    int deleteMessage(Integer msgId);

    /**
     * 新增留言回复s
     * @param userId   回复者id
     * @param toUserId 被回复者id
     * @param pid      被回复留言id
     * @param content  内容
     */
    @Insert("insert into leave_message(from_user_id,to_user_id,pid,content) " +
            "values(#{userId},#{toUserId},#{pid},#{content})")
    int replyMessage(@Param("userId") Integer userId, @Param("toUserId") Integer toUserId,
                     @Param("pid") Integer pid, @Param("content") String content);

    /**
     * 查询所有留言
     */
    List<LeaveMessage> selectAllMessage();

    /**
     * 查询指定pid留言的所有回复
     * @param pid 被回复的留言id
     */
    List<LeaveMessage> selectReplyByPid(String pid);


    /**
     * 根据留言id查找留言者id
     */
    @Select("select m.from_user_id fromUserId from leave_message m where id=#{magId}")
    String selectFromUserIdByMsgId(Integer msgId);
}
