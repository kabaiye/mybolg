package com.kabaiye.controller;

import com.kabaiye.entity.pojo.User;
import com.kabaiye.entity.vo.R;
import com.kabaiye.entity.vo.auth.LoggingRes;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.service.SmsService;
import com.kabaiye.service.TokenService;
import com.kabaiye.service.UserService;
import com.kabaiye.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private SmsService smsService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    // 向手机发送验证码
    @PostMapping("/sms/send")
    public R sendCode(@RequestParam("mobile") String mobile,
                      HttpServletRequest request) {
        String ip = HttpUtil.getClientIp(request);
        // 调用验证码发送服务
        return smsService.sendCode(mobile, ip);
    }

    /**
     * 账号密码登录
     */
    @PostMapping("/account/login")
    public R passwordLogin(@RequestParam("username") String account,
                           @RequestParam("password") String password) {
        log.info("进入passwordLogin");
        String realPassword = userService.selectPasswordByAccount(account);
        if (realPassword == null) {
            return R.error("用户不存在");
        }
        if (!realPassword.equals(password)) {
            return R.error("密码错误");
        }
        Integer userId = userService.selectIdByAccount(account);
        Integer isAdmin = userService.selectIsAdmin(userId);
        return R.ok(tokenService.creatTokenForLoggingRes(userId, isAdmin));
    }

    /**
     * 手机号验证码登录
     */
    @PostMapping("/mobile/login")
    public R mobileLogin(@RequestParam("mobile") String mobile,
                         @RequestParam("code") String code) {
        String msg = smsService.verifyMobileCode(mobile, code);
        if (msg != null) {
            return R.error(msg);
        }
        Integer userId = userService.selectIdByAccount(mobile);
        if (userId == null) {
            // 用户不存在，先注册
            userId = userService.addUser(new User(mobile));
            if (userId == null) {
                return R.error("登陆失败，请稍后重试");
            }
        }
        return R.ok(tokenService.creatTokenForLoggingRes(userId, 0));
    }

    /**
     * 退出登陆
     */
    @DeleteMapping("/logout")
    public R logout(@RequestParam("access_token") String token) {
        tokenService.deleteToken(token);
        return R.ok(null);
    }

        /**
     * 刷新access_token
     * 一般比refresh_token短，使用refresh_token刷新access_token可避免频繁登录。
     */
    @PostMapping("/refresh_access_token")
    public R refreshToken(@RequestParam("refresh_token") String refresh_token) {
        LoggingRes loggingRes = tokenService.refreshAccountToken(refresh_token);
        if (loggingRes == null) {
            R.error("refresh_token已失效，请重新登陆");
        }
        return R.ok(loggingRes);
    }

    /**
     * 第三方登录
     * type：类型，必传，1:qq，2:github，3:gitee
     * code：认证code，必传
     * 接口说明：这里的code是第三方登录回调获取到的code。
     * 登录成功：
     */
    @PostMapping("/oauth")
    public R thirdPartyLogin(@RequestParam("type") String type,
                             @RequestParam("code") String code){
        return R.ok("暂未实现接口");
    }

    /**
     * 分页查询客户端，功能已取消
     * @return
     */
    @Admin
    @GetMapping("/client/page")
    public R clientPageInfo(){
        return R.ok(new ArrayList<>());
    }
}
