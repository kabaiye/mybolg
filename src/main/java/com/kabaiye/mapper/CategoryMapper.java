package com.kabaiye.mapper;

import com.kabaiye.entity.pojo.Category;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 分类数据访问接口。
 */
@Mapper
public interface CategoryMapper {

    /**
     * 添加一个新的分类。
     *
     * @param name 分类的名称
     * @param parent_id 父分类的ID，如果是顶级分类则为0
     * @return 影响的行数
     */
    @Insert("INSERT INTO category(name, parent_id, deleted) VALUES (#{name}, #{parent_id}, 0)")
    int insertCategory(String name, String parent_id);

    /**
     * 逻辑删除一个分类。
     *
     * @param id 要删除的分类的ID
     * @return 影响的行数
     */
    @Update("UPDATE category SET deleted = 1 WHERE id = #{id} ")
    int deleteCategory(String id);

    /**
     * 修改分类的名称。
     *
     * @param id          要修改的分类的ID
     * @param name 新的分类名称
     * @return 影响的行数
     */
    @Update("UPDATE category SET name = #{name} WHERE id = #{id}")
    int updateCategory(String id, String name);

    /**
     * 查询所有分类。
     *
     * @return 所有分类的列表
     */
    @Select("SELECT id AS id, name AS name, parent_id AS parentId, deleted AS deleted FROM category WHERE deleted = 0")
    List<Category> selectAllCategories();

    /**
     * 按分类id查询分类名
     */
    @Select("select name from category where id = #{categoryId}")
    String selectNameById(String categoryId);
}

