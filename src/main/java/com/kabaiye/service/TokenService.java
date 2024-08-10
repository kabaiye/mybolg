package com.kabaiye.service;

import com.kabaiye.entity.vo.auth.LoggingRes;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Token 相关的服务接口。
 */
@Service
public interface TokenService {

    /**
     * 创建 token。
     *
     * @param userId 用户ID
     * @param isAdmin 是否为管理员
     * @return 生成的 token 字符串
     */
    String creatToken(Integer userId, Integer isAdmin);

    /**
     * 解析 token。
     *
     * @param token 待解析的 token
     * @return 包含用户 ID 和管理员状态的映射
     */
    Map<String, Integer> parseToken(String token);

    /**
     * 校验token是否被篡改
     */
    boolean isRealToken(String token);

    /**
     * 刷新 token 的有效时间，并返回新的 token。
     *
     * @param token 待刷新的 token
     * @return 新的 token 字符串
     */
    LoggingRes refreshAccountToken(String token);

    /**
     * 判断 token 是否已过期。
     *
     * @param token 待检查的 token
     * @return 如果 token 已过期则返回 true，否则返回 false
     */
    boolean isExpires(String token);

    /**
     * 将 Bearer token 转换为 token。
     *
     * @param BToken 包含 "Bearer " 前缀的 token
     * @return 不包含前缀的 token
     */
    String BTokenToToken(String BToken);

    /**
     * 创建用于登录响应的 token。
     *
     * @param userId 用户ID
     * @param isAdmin 是否为管理员
     * @return 登录响应对象
     */
    LoggingRes creatTokenForLoggingRes(Integer userId, Integer isAdmin);

    /**
     * 删除token
     */
    void deleteToken(String token);

    /**
     * 生成刷新用长效token
     */
    String creatRefreshToken(Integer userId,Integer isAdmin);

}
