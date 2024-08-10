package com.kabaiye.service;

import com.kabaiye.entity.vo.R;

public interface SmsService {
    /**
     * 验证码发送服务，传入ip是为了限制ip请求次数
     */
    R sendCode(String mobile, String ip);

    /**
     * 校验手机验证码有效性
     * @return 错误信息或null
     */
    String verifyMobileCode(String mobile, String code);
}
