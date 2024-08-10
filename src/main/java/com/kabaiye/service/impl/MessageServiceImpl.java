package com.kabaiye.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kabaiye.entity.pojo.LeaveMessage;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.message.MessageReplyRes;
import com.kabaiye.mapper.MessageMapper;
import com.kabaiye.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public int addMessage(Integer userId, String content) {
        return messageMapper.addMessage(userId, content);
    }

    @Override
    public int deleteMessage(Integer msgId) {
        return messageMapper.deleteMessage(msgId);
    }

    @Override
    public int replyMessage(Integer userId, Integer toUserId, Integer pid, String content) {
        return messageMapper.replyMessage(userId, toUserId, pid, content);
    }

    @Override
    public PageInfoObj<MessageReplyRes> getMessagePage(Integer pageNum, Integer pageSize) {

        // 所有留言及其回复
        List<MessageReplyRes> messageReplyResList = new ArrayList<>();
        // 所有留言
        PageHelper.startPage(pageNum,pageSize);
        List<LeaveMessage> leaveMessageList = messageMapper.selectAllMessage();
        // 获取对应分页数据
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(leaveMessageList);
        // 遍历对应数据封装完整返回对象
        for(LeaveMessage leaveMessage : pageInfo.getList()){
            String id = leaveMessage.getId();
            // 获取该条留言回复列表
            List<LeaveMessage> replyList = messageMapper.selectReplyByPid(id);
            // 将当前留言的发送者作为每一条回复的回复对象ToUser
            for(LeaveMessage reply:replyList){
                reply.setToUser(leaveMessage.getFromUser());
            }
            // 封装留言和他的回复
            MessageReplyRes messageReplyRes = new MessageReplyRes(
                    id,
                    leaveMessage.getContent(),
                    leaveMessage.getCreateTime(),
                    leaveMessage.getFromUser(),
                    replyList
            );
            messageReplyResList.add(messageReplyRes);
        }

        // 封装返回结果
        return new PageInfoObj<>(
                messageReplyResList,
                pageInfo.getTotal(),
                pageInfo.getSize(),
                pageInfo.getPageNum(),
                true,
                pageInfo.getPages()
        );
    }

    @Override
    public List<MessageReplyRes> getLatestMessage(Integer limit) {
        List<MessageReplyRes> messageReplyResList = new ArrayList<>();
        // 分页获取前limit个留言，因为是时间降序排序
        PageHelper.startPage(1,limit);
        List<LeaveMessage> leaveMessageList = messageMapper.selectAllMessage();
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(leaveMessageList);
        // 遍历每一个最新留言，封装为返回对象
        for(LeaveMessage leaveMessage:pageInfo.getList()){
            MessageReplyRes messageReplyRes = new MessageReplyRes(
                    leaveMessage.getId(),
                    leaveMessage.getContent(),
                    leaveMessage.getCreateTime(),
                    leaveMessage.getFromUser(),
                    new ArrayList<>() // 空列表
            );
            messageReplyResList.add(messageReplyRes);
        }
        return messageReplyResList;
    }

    @Override
    public String selectFromUserIdByMsgId(Integer msgId) {
        return messageMapper.selectFromUserIdByMsgId(msgId);
    }


}
