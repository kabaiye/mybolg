package com.kabaiye.service.impl;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.kabaiye.entity.constant.SmsConstant;
import com.kabaiye.entity.vo.auth.LoggingRes;
import com.kabaiye.service.TokenService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Token 服务的具体实现。默认使用hs256算法
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public TokenServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 创建 token，有效时间为 30 分钟。
     *
     * @param userId 用户ID
     * @param admin 是否为管理员
     * @return 生成的 token 字符串
     */
    @Override
    public String creatToken(Integer userId, Integer admin) {
        String token = JWT.create()
                .setKey(SmsConstant.JWT_KEY)
                .setPayload("userId", userId)
                .setPayload("admin", admin)
                .sign();

        // 存储 token 到 Redis 中，并设置过期时间
        redisTemplate.opsForValue().set(SmsConstant.ACCOUNT_TOKEN_KEY + token, userId.toString(),
                SmsConstant.ACCOUNT_TOKEN_EXPIRES_TIME, TimeUnit.MINUTES);

        return token;
    }

    /**
     * 解析 token。
     *
     * @param token 待解析的 token
     * @return 包含用户 ID 和管理员状态的映射
     */
    @Override
    public Map<String, Integer> parseToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        Integer userId = null;
        Integer admin = null;
        try {
            userId = Integer.parseInt(jwt.getPayload("userId").toString());
            admin = Integer.parseInt(jwt.getPayload("admin").toString());
        } catch (NullPointerException e) {
            log.info("空指针异常，出现在parseToken,可忽略");
        }
        Map<String, Integer> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("admin", admin);
        return payload;
    }

    /**
     * 校验token是否被篡改
     */
    public boolean isRealToken(String token){
        return JWT.of(token).setKey(SmsConstant.JWT_KEY).verify();
    }

    /**
     * 通过refresh_token刷新 account_token 。
     *
     * @param refreshToken 待刷新的 token
     * @return 新的 account_token 字符串
     */
    @Override
    @Nullable
    public LoggingRes refreshAccountToken(String refreshToken) {
        if (refreshToken==null || isExpires(refreshToken)){
            return null;
        }
        return creatTokenForLoggingRes(
                parseToken(refreshToken).get("userId"),
                parseToken(refreshToken).get("admin"));
    }

    /**
     * 判断 token 是否已过期。
     *
     * @param token 待检查的 token
     * @return 如果 token 已过期则返回 true，否则返回 false
     */
    @Override
    public boolean isExpires(String token) {
        log.info("进入isExpires，token：“{}”", token);
        return redisTemplate.opsForValue().get(SmsConstant.ACCOUNT_TOKEN_KEY + token) == null
                && redisTemplate.opsForValue().get(SmsConstant.REFRESH_TOKEN_KEY + token) == null;
    }

    /**
     * 将 Bearer token 转换为 token。
     *
     * @param BToken 包含 "Bearer " 前缀的 token
     * @return 不包含前缀的 token
     */
    @Override
    public String BTokenToToken(String BToken) {
        return BToken.replaceAll("Bearer ", "");
    }

    /**
     * 创建用于登录响应的 token。
     * @param userId 用户ID
     * @param admin 是否为管理员
     * @return 登录响应对象
     */
    @Override
    public LoggingRes creatTokenForLoggingRes(Integer userId, Integer admin) {
        String accountToken = creatToken(userId, admin);
        String refreshToken = creatRefreshToken(userId,admin);
        // Bearer
        // log.info("准备导出token：{}", account_token);
        return new LoggingRes(accountToken, "Bearer", refreshToken);
    }

    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(SmsConstant.ACCOUNT_TOKEN_KEY + token);
    }

    @Override
    public String creatRefreshToken(Integer userId, Integer admin) {
        String token = JWT.create()
                .setKey(SmsConstant.JWT_KEY)
                .setPayload("userId", userId)
                .setPayload("admin", admin)
                // 增加字段确保和accountToken不相同
                .setPayload("isRefresh","1")
                .sign();

        // 存储 token 到 Redis 中，并设置过期时间为1天
        redisTemplate.opsForValue().set(SmsConstant.REFRESH_TOKEN_KEY + token, "1",
                SmsConstant.REFRESH_TOKEN_EXPIRES_TIME,TimeUnit.MINUTES);

        return token;
    }
}
