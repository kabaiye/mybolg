package com.kabaiye.entity.pojo;

import lombok.Data;

/**
 * 客户端实体类
 */
@Data
public class Client {

    /**
     * 客户端ID
     */
    private Long id;

    /**
     * 客户端标识
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * access_token 的有效时长（单位：秒）
     */
    private Integer accessTokenExpire;

    /**
     * refresh_token 的有效时长（单位：秒）
     */
    private Integer refreshTokenExpire;

    /**
     * 是否启用 refresh_token，1: 是，0: 否
     */
    private Integer enableRefreshToken;
}