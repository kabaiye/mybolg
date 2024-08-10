package com.kabaiye.entity.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS(0, "成功"),
    USER_NAME_OR_PASSWORD_ERROR(101, "用户名或密码错误"),
    USER_NOT_EXIST(102, "用户不存在"),
    ACCOUNT_DISABLED(103, "账号已禁用"),
    ACCOUNT_EXPIRED(104, "账号已过期"),
    ACCOUNT_LOCKED(105, "账号已锁定"),
    CREDENTIALS_EXPIRED(106, "凭证已过期"),
    ACCESS_DENIED(107, "不允许访问"),
    INSUFFICIENT_PERMISSION(108, "无权限访问"),
    INVALID_CREDENTIALS(109, "凭证无效或已过期"),
    INVALID_REFRESH_TOKEN(110, "刷新凭证无效或已过期"),
    INVALID_REQUEST(111, "无效请求或请求不接受"),
    INTERFACE_LIMIT_EXCEEDED(112, "接口访问次数限制"),
    SYSTEM_ERROR(113, "系统错误");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
