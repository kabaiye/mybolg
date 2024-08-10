package com.kabaiye.service;


import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.message.MessageReplyRes;

import java.util.List;

public interface MessageService {

    /**
     * 添加留言
     */
    int addMessage(Integer userId, String content);

    /**
     * 删除留言
     */
    int deleteMessage(Integer msgId);

    /**
     * 新增留言回复
     */
    int replyMessage(Integer userId, Integer toUserId, Integer pid, String content);

    /**
     * 分页获取留言及其回复
     */
    PageInfoObj<MessageReplyRes> getMessagePage(Integer pageNum, Integer pageSize);

    /**
     * 获取最新留言，无需回复列表
     */
    List<MessageReplyRes> getLatestMessage(Integer limit);

    /**
     * 根据留言id查找对应的留言者id
     */
    String selectFromUserIdByMsgId(Integer msgId);
}
