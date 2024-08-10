package com.kabaiye.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.kabaiye.entity.constant.SmsConstant;
import com.kabaiye.entity.vo.R;
import com.kabaiye.service.SmsService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 向手机发送验证码
    @Override
    public R sendCode(String mobile,String ip) {
        // 随机6位数字
        String code = RandomUtil.randomNumbers(6);
        // 下面假设调用短信平台发送了验证码
        // sms(mobile,code)
        log.info("验证码发送成功,验证码为：{}", code);

        // 保存验证码和手机号到redis
        // 默认情况下，验证码5分钟内有效，同一手机号每天只能发10次；同一ip每天只能发10次；同一手机号限流120s一次。

        // 距离上一次发送的时间TTL
        Long time = redisTemplate.getExpire(SmsConstant.LIMIT_SMS_TIME + mobile, TimeUnit.SECONDS);
        if (time != null && time>=0) {
            return R.error(StrUtil.format("发送验证码太快了，请{}秒后重试", time));
        }

        // 获取手机号发送次数
        String mobileCount = redisTemplate.opsForValue().get(SmsConstant.LIMIT_SMS_MOBILE_COUNT + mobile);
        if (mobileCount == null) {
            redisTemplate.opsForValue().increment(SmsConstant.LIMIT_SMS_MOBILE_COUNT + mobile, 0);
            redisTemplate.expire(SmsConstant.LIMIT_SMS_MOBILE_COUNT + mobile, 1, TimeUnit.DAYS);
        } else if (Integer.parseInt(mobileCount) >= 10) {
            return R.error("同一手机号每天只能发10次");
        }

        // 查询ip发送次数
        String ipCount = redisTemplate.opsForValue().get(SmsConstant.LIMIT_SMS_IP_COUNT + ip);
        if (ipCount == null) {
            ipCount = "0";
            redisTemplate.opsForValue().set(SmsConstant.LIMIT_SMS_IP_COUNT + ip, ipCount);
            redisTemplate.expire(SmsConstant.LIMIT_SMS_IP_COUNT + ip, 1, TimeUnit.DAYS);
        } else if (Integer.parseInt(ipCount) >= 10) {
            return R.error("同一ip每天只能发10次");
        }

        // 存储手机号和验证码，有效期5min
        redisTemplate.opsForValue().set(SmsConstant.LIMIT_SMS_CODE + mobile, code);
        redisTemplate.expire(SmsConstant.LIMIT_SMS_CODE + mobile, 5, TimeUnit.MINUTES);

        // 增加手机号发送的次数
        redisTemplate.opsForValue().increment(SmsConstant.LIMIT_SMS_MOBILE_COUNT + mobile, 1);

        // 设置号码限制120s一次，有limit:sms:code:键说明被限制
        redisTemplate.opsForValue().set(SmsConstant.LIMIT_SMS_TIME + mobile, "re");
        redisTemplate.expire(SmsConstant.LIMIT_SMS_TIME + mobile, 120, TimeUnit.SECONDS);

        // 增加ip发送的次数
        redisTemplate.opsForValue().increment(SmsConstant.LIMIT_SMS_IP_COUNT + ip, 1);

        // 假设发送验证码成功
        return R.ok(null);
    }

    @Override
    @Nullable
    public String verifyMobileCode(String mobile, String code) {
        String reallyCode = redisTemplate.opsForValue().get(SmsConstant.LIMIT_SMS_CODE + mobile);
        if (reallyCode == null) {
            return "验证码已过期，请重试";
        }
        if (!reallyCode.equals(code)) {
            return "验证码错误";
        }
        return null;
    }

}
