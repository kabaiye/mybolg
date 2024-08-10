package com.kabaiye.entity.pojo;

import lombok.Data;

/**
 * 友链实体类
 */
@Data
public class FriendLink {

    /**
     * 友链ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 图标路径
     */
    private String icon;
}

