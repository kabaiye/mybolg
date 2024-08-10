package com.kabaiye.controller;

import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.R;
import com.kabaiye.entity.vo.message.MessageReplyRes;
import com.kabaiye.myInterface.Logging;
import com.kabaiye.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    /**
     * 新增留言
     * 请求方法：POST
     * 请求地址：/leave/message/add
     * 请求参数：content：内容，必传
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 个人博客系统默认只有一个管理员，所以留言就保存在一个地方无需收到留言的用户id
     * 接口说明：留言会给管理员发送留言提醒邮件。
     */
    @Logging
    @PostMapping("/leave/message/add")
    public R addMessage(@RequestParam("content") String content,
                        HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        int res = messageService.addMessage(userId,content);
        if (res <= 0){
            return R.error("留言失败");
        }
        return R.ok(null);
    }

    /**
     * 删除留言或回复
     * 请求方法：DELETE
     * 请求地址：/leave/message/delete/{id}
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 接口说明：逻辑删除；本人或管理员可删除。
     */
    @Logging
    @DeleteMapping("/leave/message/delete/{id}")
    public R deleteMessage(@PathVariable("id") Integer msgId,
                           HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        Integer admin =  (Integer)request.getAttribute("admin");
        String fromUserId =  messageService.selectFromUserIdByMsgId(msgId);
        if(!userId.toString().equals(fromUserId) && admin!=1){
            return R.error("无权限删除");
        }
        int res = messageService.deleteMessage(msgId);
        if (res <= 0){
            return R.error("删除失败");
        }
        return R.ok(null);
    }

    /**
     * 新增留言回复
     * 请求方法：POST
     * 请求地址：/leave/message/reply
     * pid：父(留言)id，必传
     * toUserId：被回复者id，必传
     * content：内容，必传
     * 需要access_token： 是
     * 需要管理员权限： 否
     * 接口说明：留言回复会发送留言回复提醒邮件给被回复者并抄送到管理员。
     */
    @Logging
    @PostMapping("/leave/message/reply")
    public R replyMessage(@RequestParam("pid") Integer pid,
                          @RequestParam("toUserId") Integer toUserId,
                          @RequestParam("content") String content,
                          HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        int res = messageService.replyMessage(userId,toUserId,pid,content);
        if(res<=0){
            return R.error("回复失败");
        }
        return R.ok(null);
    }

    /**
     * 分页获取留言与回复
     * 请求方法：GET
     * 请求地址：/leave/message/page
     * 请求参数：
     * current：当前页，非必传，默认1
     * size：每页数量，非必传，默认5
     * 需要access_token： 否
     * 需要管理员权限： 否
     * 接口说明：留言下挂回复列表；按时间降序排序。
     */
    @GetMapping("/leave/message/page")
    public R getMessagePage(@RequestParam(value = "current",defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "size",defaultValue = "5") Integer pageSize){
        PageInfoObj<MessageReplyRes> resp = messageService.getMessagePage(pageNum,pageSize);
        return R.ok(resp);
    }

    /**
     *最新留言列表
     * 请求方法：GET
     * 请求地址：/leave/message/latest
     * 请求参数：limit：数量，非必传，默认5
     * 需要access_token： 否
     * 需要管理员权限： 否
     * 获取成功：MessageReplyRes playList=[]
     */
    @GetMapping("/leave/message/latest")
    public R getLatestMessage(@RequestParam(value = "limit",defaultValue = "5") Integer limit){
        List<MessageReplyRes> resp = messageService.getLatestMessage(limit);
        return R.ok(resp);
    }
}
