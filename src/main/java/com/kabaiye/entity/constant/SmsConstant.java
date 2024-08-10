package com.kabaiye.entity.constant;

/**
 * 验证相关的常量
 */
public class SmsConstant {
    // 手机号上一次发送的时间
    public static final String LIMIT_SMS_TIME = "limit:sms:time:";
    // 手机号一天内发送的次数
    public static final String LIMIT_SMS_MOBILE_COUNT = "limit:sms:mobileCount:";
    // ip一天内发送的次数
    public static final String LIMIT_SMS_IP_COUNT = "limit:sms:ipCount:";
    // 手机号验证码
    public static final String LIMIT_SMS_CODE = "limit:sms:code:";
    // account_token失效时间，min
    public static final Long ACCOUNT_TOKEN_EXPIRES_TIME = 120L;
    // refresh_token失效时间，min
    public static final Long REFRESH_TOKEN_EXPIRES_TIME = 120L;
    // account_token在redis存储的前缀
    public static final String ACCOUNT_TOKEN_KEY = "user:token:account:";
    // refresh_token在redis存储的前缀
    public static final String REFRESH_TOKEN_KEY = "user:token:refresh:";
    // jwt秘钥
    public static final byte[] JWT_KEY = "kabaiyeKey".getBytes();


}
