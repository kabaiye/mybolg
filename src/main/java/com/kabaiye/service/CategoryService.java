package com.kabaiye.service;


import com.kabaiye.entity.pojo.Category;
import com.kabaiye.entity.vo.PageInfoObj;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 分类服务接口，提供对分类数据的操作方法。
 */
public interface CategoryService {

    /**
     * 添加一个新的分类。
     *
     * @param categoryName 分类的名称
     * @param parentId     父分类的ID，如果是顶级分类则为0
     * @return 影响的行数，大于0表示成功
     */
    int addCategory(String categoryName, String parentId);

    /**
     * 逻辑删除一个分类。
     *
     * @param id 要删除的分类的ID
     * @return 影响的行数，大于0表示成功
     */
    int deleteCategory(String id);

    /**
     * 修改分类的名称。
     *
     * @param id          要修改的分类的ID
     * @param categoryName 新的分类名称
     * @return 影响的行数，大于0表示成功
     */
    int updateCategory(String id, String categoryName);

    /**
     * 分页查询分类信息。
     *
     * @param pageNum 当前页码
     * @param pageSize 每页显示的记录数
     * @return 分页查询结果对象
     */
    PageInfoObj<Category> pageInfoCategory(int pageNum, int pageSize);

    /**
     * 获取所有分类的列表。
     *
     * @return 所有分类的列表
     */
    List<Category> infoCategory();

    /**
     * 按id查询分类名
     */
    String selectNameById(String categoryId);
}

