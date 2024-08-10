package com.kabaiye.entity.vo.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册请求体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBody {

    @Pattern(regexp = "^\\d{6}$", message = "验证码必须为6位数字")
    private String code; // 验证码

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号必须为有效的11位手机号")
    private String mobile; // 手机号

    @Size(min = 6, message = "密码不能少于6位数")
    private String password; // 密码

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{1,15}$", message = "用户名只能字母开头，允许2-16字节，允许字母数字下划线")
    private String username; // 用户名
}
