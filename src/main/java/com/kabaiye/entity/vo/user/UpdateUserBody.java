package com.kabaiye.entity.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserBody {
    private String birthday; // 生日
    private String brief; // 简介
    private String gender; // 性别，1：男，0：女
    private String nickname; // 昵称
    private Integer userId; // 当前用户id
}
