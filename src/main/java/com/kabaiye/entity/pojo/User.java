package com.kabaiye.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.kabaiye.entity.constant.DateConstant.DATE_FORMAT;

/**
 * 用户的信息。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 用户ID。
     */
    private Integer userId;

    /**
     * 用户名。
     */
    private String username;

    /**
     * 密码。
     */
    private String password;

    /**
     * 手机号。
     */
    private String mobile;

    /**
     * 昵称。
     */
    private String nickname;

    /**
     * 性别，1: 男，0: 女。
     */
    private Integer gender;

    /**
     * 生日。
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime birthday;

    /**
     * 邮箱。
     */
    private String email;

    /**
     * 个性签名。
     */
    private String brief;

    /**
     * 头像。
     */
    private String avatar;

    /**
     * 状态，0: 正常，1: 锁定，2: 禁用，3: 过期。
     */
    private Integer status;

    /**
     * 是否管理员，1: 是，0: 否。
     */
    private Integer admin;

    /**
     * 注册时间。
     */
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime createTime;

    public User(String mobile) {
        this.mobile = mobile;
    }

    public User(String username, String password, String mobile) {
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }

    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            String formattedDateTime = value.format(formatter);
            gen.writeString(formattedDateTime);
        }
    }
}






