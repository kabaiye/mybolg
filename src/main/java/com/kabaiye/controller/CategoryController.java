package com.kabaiye.controller;

import com.kabaiye.entity.pojo.Category;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.entity.vo.R;
import com.kabaiye.entity.vo.category.AddCategoryBody;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 新增分类
     */
    @Admin
    @PostMapping("/add")
    public R addCategory(@RequestBody AddCategoryBody body){
        String categoryName = body.getName();
        int res = categoryService.addCategory(categoryName,body.getParentId());
        if(res<=0){
            return R.error("添加失败");
        }
        return R.ok(null);
    }
    /**
     * 接口说明：逻辑删除分类。
     */
    @Admin
    @DeleteMapping("/delete/{id}")
    public R deleteCategory(@PathVariable("id") String id){
        int res = categoryService.deleteCategory(id);
        if(res<=0){
            return R.error("删除失败");
        }
        return R.ok(null);
    }

    /**
     * 接口说明：分类修改只可修改分类名。
     */
    @Admin
    @PostMapping("/update")
    public R updateCategory(@RequestParam("id") String categoryId,
                       @RequestParam("name") String categoryName){
        int res = categoryService.updateCategory(categoryId,categoryName);
        if(res<=0){
            return R.error("修改失败");
        }
        return R.ok(null);
    }

    /**
     * 分页查询分类，需要管理员
     */
    @Admin
    @GetMapping("/page")
    public R pageInfoCategory(@RequestParam(value = "current",required = false,defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "size",required = false,defaultValue = "5") Integer pageSize){
        PageInfoObj<Category> resp = categoryService.pageInfoCategory(pageNum,pageSize);
        return R.ok(resp);
    }

    /**
     * 获取分类列表, 不需要管理员
     */
    @GetMapping({"/list","/tree"})
    public R infoCategory(){
        List<Category> category = categoryService.infoCategory();
        return R.ok(category);
    }
}
