package com.kabaiye.entity.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存放登陆成功后返回的token响应数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggingRes {
    private String access_token;
    private String token_type;
    private String refresh_token;
}
