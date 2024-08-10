package com.kabaiye.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.kabaiye.entity.vo.user.PageInfoRes;
import com.kabaiye.entity.vo.user.RegisterBody;
import com.kabaiye.entity.constant.UserConstant;
import com.kabaiye.entity.pojo.User;
import com.kabaiye.entity.vo.R;
import com.kabaiye.exception.FileUploadException;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.myInterface.Logging;
import com.kabaiye.service.FileService;
import com.kabaiye.service.SmsService;
import com.kabaiye.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.kabaiye.entity.constant.DateConstant.DATE_FORMAT;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 注册
     */
    @PostMapping("/register")
    public R register(@Valid @RequestBody RegisterBody body){
        String mobile = body.getMobile();
        String code = body.getCode();
        String password = body.getPassword();
        String username = body.getUsername();
        String msg = smsService.verifyMobileCode(mobile,code);
        if(msg!=null){
            return R.error(msg);
        }
        // 校验通过
        User user = new User(username,password,mobile);
        Integer userId = userService.addUser(user);
        if(userId==null){
            return R.error("添加失败，请稍后重试");
        }
        return R.ok(null);
    }

    /**
     * 获取用户信息
     * 接口说明：前端权限菜单可根据roles生成；用户头像可能不存在，默认头像前端指定即可。
     */
    @Logging
    @GetMapping("/info")
    public R selectUser(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        User stringUser = userService.selectUser(userId);

        Map<String,Object> res = BeanUtil.beanToMap(stringUser);
        // 添加roles字段
        List<String> roles = new ArrayList<>();
        if(stringUser.getAdmin()==1){
            roles.add(UserConstant.USER_PERMISSION_ADMIN);
        }else{
            roles.add(UserConstant.USER_PERMISSION_ORDINARY);
        }
        res.put("roles",roles);

        // 修改时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        res.put("birthday",stringUser.getBirthday()==null?null:stringUser.getBirthday().format(formatter));
        res.put("createTime", stringUser.getCreateTime()==null?null:stringUser.getCreateTime().format(formatter));
        return R.ok(res);
    }

    /**
     * 分页查询用户信息
     * 接口说明：用于后台管理，支持用户名和昵称模糊查询。
     */
    @Logging
    @Admin
    @GetMapping("/page")
    public R selectAll(@RequestParam(value = "current",defaultValue="1") Integer pageNum,
                       @RequestParam(value = "size",defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "username",required = false) String username,
                       @RequestParam(value = "nickname",required = false) String nickname){
        PageInfoRes pageInfoRes = userService.selectAll(pageNum,pageSize,username,nickname);
        return R.ok(pageInfoRes);
    }

    /**
     *修改密码
     */
    @Logging
    @PostMapping("/password/update")
    public R updatePassword(@RequestParam("oldPassword") String oldPassword,
                            @RequestParam("newPassword") String newPassword,
                            HttpServletRequest request){
        if(newPassword.length()<6){
            return R.error("密码不能少于6位");
        }
        Integer userId = (Integer) request.getAttribute("userId");
        // 校验密码正确性
        String realPassword = userService.selectPassword(userId);
        if(!realPassword.equals(oldPassword)){
            return R.error("原密码错误！");
        }
        // 修改密码
        int res = userService.updatePassword(userId,newPassword);
        if(res<=0){
            R.error("修改失败，请稍后重试");
        }
        return R.ok(null);
    }

    /**
     * 忘记密码/重置密码
     * 接口说明：验证码调发送验证码接口获取；密码不能少于6位数。
     */
    @PostMapping("/password/reset")
    public R resetPassword(@RequestParam("mobile") String mobile,
                           @RequestParam("code") String code,
                           @RequestParam("password") String password){
        if(password.length()<6){
            return R.error("密码不可少于6位");
        }
        // 校验验证码
        String msg = smsService.verifyMobileCode(mobile,code);
        if(msg!=null){
            return R.error(msg);
        }
        // 修改密码
        int res = userService.updatePasswordByMobile(mobile, password);
        if(res<=0){
            return R.error("重置失败，请稍后重试");
        }
        return R.ok(null);
    }

    /**
     * 用户状态修改
     * 接口说明：用于禁用、锁定用户等操作。
     */
    @Logging
    @Admin
    @PostMapping("/status/update")
    public R updateStatus(@RequestParam("userId") Integer userId,
                          @RequestParam("status") Integer status){
        int res = userService.updateStatus(userId,status);
        if(res<=0){
            return R.error("修改失败，请稍后重试");
        }
        return R.ok(null);
    }

    /**
     * 用于原用户名为空时（第三方登录）绑定用户名。
     */
    @Logging
    @PostMapping("/username/bind")
    public R bindUsername(@RequestParam("username")String username,
                          HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        int res = userService.bindUsername(userId,username);
        if(res<=0){
            return R.error("绑定失败，请稍后重试");
        }
        return R.ok(null);
    }

    /**
     * 更新用户基本信息
     * {"birthday": "生日",
     * "brief": "简介","gender": "性别，1：男，0：女",
     * "nickname": "昵称","userId": "当前用户id"}
     */
    @Logging
    @PostMapping("/update")
    public R updateUser(@RequestBody User user,
                        HttpServletRequest request){
        Integer userId =(Integer) request.getAttribute("userId");
        user.setUserId(userId);
        int res = userService.updateUser(user);
        if(res<=0){
            return R.error("修改失败，请稍后重试");
        }
        return R.ok(null);
    }

    /**
     * 绑定手机号
     * 接口说明：该接口只用于原手机号为空时（第三方登录）绑定手机号；验证码调发送验证码接口获取。
     */
    @Logging
    @PostMapping("/mobile/bind")
    public R bindMobile(@RequestParam("mobile") String mobile,
                        @RequestParam("code") String code,
                        HttpServletRequest request){
        String msg = smsService.verifyMobileCode(mobile, code);
        // 验证码校验失败
        if(msg!=null){
            return R.error(msg);
        }
        Integer userId = (Integer) request.getAttribute("userId");
        int res = userService.updateMobile(userId,mobile);
        if(res<=0){
            return R.error("绑定失败，请稍后重试！");
        }
        return R.ok(null);
    }

    /**
     * 更改手机号更换手机号
     */
    @Logging
    @PostMapping("/mobile/rebind")
    public R updateMobile(@RequestParam("mobile") String mobile,
                          @RequestParam("code") String code,
                          HttpServletRequest request){
        String msg = smsService.verifyMobileCode(mobile, code);
        if(msg!=null){
            return R.error(msg);
        }
        Integer userId = (Integer) request.getAttribute("userId");
        int res = userService.updateMobile(userId,mobile);
        if(res<=0){
            return R.error("保存失败，请稍后重试！");
        }
        return R.ok(null);
    }

    /**
     * 更新用户头像
     */
    @Logging
    @PostMapping("/avatar/update")
    public R updateAvatar(@RequestPart(value = "file") MultipartFile file,
                          HttpServletRequest request)
            throws FileUploadException {
        Integer userId = (Integer) request.getAttribute("userId");
        log.info("用户{} 进入更新头像/avatar/update",userId);
        // 1. 如果文件大于5MB，失败
        if(file.getSize() > 5*1024*1024){
            return R.error("头像大小不能超过5MB");
        }

        // 2.如果后缀不支持，失败
        // 允许的格式"bmp","gif","jpeg","png","webp","jpg"
        List<String> suffixList = Arrays.asList("bmp", "gif", "jpeg", "png", "webp", "jpg");
        // 2.1 获取上传的文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return R.error("获取文件名失败!");
        }
        // 2.2 获取后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        if(!suffixList.contains(suffix)) {
            return R.error("文件格式不支持!");
        }
        String fileName = "avatar"+userId+"_"+ RandomUtil.randomString(15) +"."+ suffix;
        // 保存文件到本地，返回文件访问uri
        String fileUri = fileService.saveFile(file, UserConstant.AVATARS_UPLOAD_DIR, fileName);
        log.info("头像文件url:{}",fileUri);

        // 更新头像uri到用户表
        int res = userService.updateAvatar(userId,fileUri);
        if(res<=0){
            return R.error("修改失败，请重试");
        }
        return R.ok(null);
    }

    /**
     * 验证邮箱code有效性，绑定
     * */
    @Logging
    @PostMapping("/email/bind")
    public R bindMail(@RequestParam("code") String code){
        log.info("/email/bind（验证邮箱有效性）被访问");
        // 从redis中查找code，然后绑定
        Map<Object, Object> resMap = redisTemplate.opsForHash().entries(UserConstant.EMAIL_VERIFY_KEY+code);
        if(resMap.isEmpty()){
            return R.error("验证失败,请稍后重试");
        }
        String userId = (String) resMap.get("userId");
        String email = (String) resMap.get("email");
        int res = userService.bindMail(userId, email);
        if(res<=0){
            return R.error("修改失败，请稍后重试");
        }
        return R.ok(null);
    }
    /**
    * 后端发送验证邮箱，将code和用户邮箱在redis进行绑定记录
    * 前端收到邮箱验证重定向链接会拿到code，然后请求"/email/bind"
    */
    @PostMapping("/email/validate")
    public R VeMail(@RequestParam("email") String email,
                    HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        String code = UUID.randomUUID(true).toString();
        // 调用邮箱验证服务发送验证邮箱
        // emailService(email, code)
        // 调用服务层VeMail方法，传入code，userId，email，存入redis
        Integer res = userService.VeMail(userId,email,code);
        if(res<=0){
            return R.error("邮件发送失败");
        }
        return R.ok(null);
    }

    /**
     * 验证原手机号
     */
    @Logging
    @PostMapping("/mobile/validate")
    public R validateMobile(@RequestParam("mobile") String mobile,
                            @RequestParam("code") String code){
        String msg = smsService.verifyMobileCode(mobile, code);
        if(msg!=null){
            return R.error(msg);
        }
        return R.ok(null);
    }


}
