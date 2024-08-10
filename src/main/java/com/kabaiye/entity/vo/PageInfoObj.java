package com.kabaiye.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页信息返回对象
 * 用于封装分页查询结果的相关信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoObj<T> {
    // 分页查询结果记录列表
    private List<T> records;
    // 总记录数
    private Long total;
    // 每页显示的记录数
    private Integer size;
    // 当前页码
    private Integer current;
    // 是否进行搜索条件总数查询
    private Boolean searchCount;
    // 总页数
    private Integer pages;
}
