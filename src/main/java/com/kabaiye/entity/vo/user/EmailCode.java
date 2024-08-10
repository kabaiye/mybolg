package com.kabaiye.entity.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存放邮箱验证消息的redis实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailCode {
    private Integer userId;
    private String email;
    private String code;
}
