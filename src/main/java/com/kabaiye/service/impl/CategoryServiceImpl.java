package com.kabaiye.service.impl;

import com.kabaiye.service.CategoryService;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kabaiye.entity.pojo.Category;
import com.kabaiye.entity.vo.PageInfoObj;
import com.kabaiye.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 分类服务的具体实现。
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 添加一个新的分类。
     *
     * @param categoryName 分类的名称
     * @param parentId     父分类的ID，如果是顶级分类则为0
     * @return 影响的行数，大于0表示成功
     */
    @Override
    public int addCategory(String categoryName, String parentId) {
        return categoryMapper.insertCategory(categoryName, parentId);
    }

    /**
     * 逻辑删除一个分类。
     *
     * @param id 要删除的分类的ID
     * @return 影响的行数，大于0表示成功
     */
    @Override
    public int deleteCategory(String id) {
        return categoryMapper.deleteCategory(id);
    }

    /**
     * 修改分类的名称。
     *
     * @param id          要修改的分类的ID
     * @param categoryName 新的分类名称
     * @return 影响的行数，大于0表示成功
     */
    @Override
    public int updateCategory(String id, String categoryName) {
        return categoryMapper.updateCategory(id, categoryName);
    }

    /**
     * 分页查询分类信息。
     *
     * @param pageNum 当前页码
     * @param pageSize 每页显示的记录数
     * @return 分页查询结果对象
     */
    @Override
    public PageInfoObj<Category> pageInfoCategory(int pageNum, int pageSize) {
        // 设置分页参数
        PageHelper.startPage(pageNum, pageSize);

        // 查询所有分类
        List<Category> categories = categoryMapper.selectAllCategories();

        // 创建 PageInfo 对象
        PageInfo<Category> pageInfo = new PageInfo<>(categories);

        // 将 PageInfo 转换为自定义的 PageInfoObj
        return new PageInfoObj<>(
                pageInfo.getList(),
                pageInfo.getTotal(),
                pageInfo.getSize(),
                pageInfo.getPageNum(),
                true,
                pageInfo.getPages()
        );
    }

    /**
     * 获取所有分类的列表。
     *
     * @return 所有分类的列表
     */
    @Override
    public List<Category> infoCategory() {
        return categoryMapper.selectAllCategories();
    }

    @Override
    public String selectNameById(String categoryId) {
        return categoryMapper.selectNameById(categoryId);
    }
}
