package com.kabaiye.entity.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUser {
    private Integer userId;
    private String nickname;
    private String avatar;
}

