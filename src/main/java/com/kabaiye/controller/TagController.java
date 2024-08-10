package com.kabaiye.controller;

import com.kabaiye.entity.pojo.Tag;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.R;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 新增标签
     */
    @Admin
    @PostMapping("/add")
    public R addTag(@RequestParam("tagName") String tagName){
        if(tagName==null){
            return R.error("无标签");
        }
        int res = tagService.addTag(tagName);
        if(res<=0){
            return R.error("添加失败");
        }
        return R.ok(null);
    }
    /**
     * 接口说明：逻辑删除标签。
     */
    @Admin
    @DeleteMapping("/delete/{id}")
    public R deleteTag(@PathVariable("id") String id){
        int res = tagService.deleteTag(id);
        if(res<=0){
            return R.error("删除失败");
        }
        return R.ok(null);
    }

    /**
     * 接口说明：标签修改只可修改标签名。
     */
    @Admin
    @PostMapping("/update")
    public R updateTag(@RequestParam("id") String tagId,
                       @RequestParam("name") String tagName){
        int res = tagService.updateTag(tagId,tagName);
        if(res<=0){
            return R.error("修改失败");
        }
        return R.ok(null);
    }

    /**
     * 分页查询标签，需要管理员，支持标签名模糊查询。
     */
    @Admin
    @GetMapping("/page")
    public R pageInfoTag(@RequestParam(value = "current",required = false,defaultValue = "1") Integer pageNum,
                     @RequestParam(value = "size",required = false,defaultValue = "5") Integer pageSize,
                     @RequestParam(value = "tagName",required = false) String tagName){
        PageInfoObj<Tag> resp = tagService.pageInfoTag(pageNum,pageSize,tagName);
        return R.ok(resp);
    }

    /**
     * 获取标签列表, 不需要管理员
     * 接口说明：支持标签名模糊查询。
     */
    @GetMapping("/list")
    public R infoTag(@RequestParam(value = "tagName",required = false) String tagName){
        List<Tag> tags = tagService.infoTag(tagName);
        return R.ok(tags);
    }
}
