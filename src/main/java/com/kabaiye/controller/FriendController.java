package com.kabaiye.controller;

import com.kabaiye.entity.pojo.FriendLink;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.R;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.service.FileService;
import com.kabaiye.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendController {
    @Autowired
    private FriendService friendService;
    /**
     * 保存友链
     * 请求方法：POST
     * 请求地址：/friend/link/save
     * 请求数据格式：application/json
     * 需要access_token： 是
     * 需要管理员权限： 是
     * 请求体数据格式：{
     *   "icon": "图标",
     *   "id": "id",
     *   "name": "名称",
     *   "url": "链接"
     * }
     */
    @Admin
    @PostMapping("/friend/link/save")
    public R saveFriendLink(@RequestBody FriendLink friendLink){
        int res =friendService.saveFriendLink(friendLink);
        if(res<=0){
            return R.error("保存失败");
        }
        return R.ok(null);
    }

    /**
     * 删除友链
     * 请求方法：DELETE
     * 请求地址：/friend/link/delete/{id}
     * 需要access_token： 是
     * 需要管理员权限： 是
     * 接口说明：物理删除。
     * 删除成功：
     */
    @Admin
    @DeleteMapping("/friend/link/delete/{id}")
    public R deleteFriendLink(@PathVariable("id") String id){
        int res = friendService.deleteFriendLink(id);
        if(res<=0){
            return R.error("删除失败");
        }
        return R.ok(null);
    }

    /**
     * 获取友链列表
     * 请求方法：GET
     * 请求地址：/friend/link/list
     * 需要access_token： 否
     * 需要管理员权限： 否
     * 获取成功：
     */
    @GetMapping("/friend/link/list")
    public R infoFriendLink(){
        List<FriendLink> resp = friendService.infoFriendLink();
        return R.ok(resp);
    }

    /**
     * 分页获取友链
     * 请求方法：GET
     * 请求地址：/friend/link/page
     * 请求参数：
     * current：当前页，非必传，默认1
     * size：每页数量，非必传，默认5
     * 需要access_token： 否
     * 需要管理员权限： 否
     */
    @GetMapping("/friend/link/page")
    public R pageInfoFriendLink(
            @RequestParam(value = "current",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(value = "size",required = false,defaultValue = "5") Integer pageSize){
        PageInfoObj<FriendLink> resp = friendService.pageInfoFriendLink(pageNum,pageSize);
        return R.ok(resp);
    }
}
