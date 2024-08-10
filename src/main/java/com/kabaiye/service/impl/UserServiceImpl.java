package com.kabaiye.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageInfo;
import com.kabaiye.entity.vo.user.PageInfoRes;
import com.kabaiye.entity.vo.user.EmailCode;
import com.kabaiye.entity.pojo.User;
import com.kabaiye.mapper.UserMapper;
import com.kabaiye.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.kabaiye.entity.constant.UserConstant.EMAIL_VERIFY_KEY;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Integer VeMail(Integer userId, String email, String code) {
        EmailCode emailCode = new EmailCode(userId, email, code);
        redisTemplate.opsForHash().putAll(EMAIL_VERIFY_KEY + code,
                BeanUtil.beanToMap(emailCode));
        // TTL 5 min
        redisTemplate.expire(EMAIL_VERIFY_KEY + userId, 5, TimeUnit.MINUTES);
        return 1;
    }

    @Override
    public int updateAvatar(Integer userId, String fileUri) {
        return userMapper.updateAvatar(userId, fileUri);
    }

    @Override
    public int updateMobile(Integer userId, String mobile) {
        return userMapper.updateMobile(userId, mobile);
    }

    @Override
    public int bindMail(String userId, String email) {
        return userMapper.bindMail(userId, email);
    }

    @Override
    public Integer addUser(User user) {
        // 生成默认昵称
        String nike = "博友_" + RandomUtil.randomString(15);
        user.setNickname(nike);
        user.setCreateTime(LocalDateTime.now());
        userMapper.addUser(user);
        return user.getUserId();
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User selectUser(Integer userId) {
        log.info("进入userService.selectUser, userId：{}", userId);
        return userMapper.selectUser(userId);
    }

    @Override
    public PageInfoRes selectAll(Integer pageNum, Integer pageSize, String username, String nickname) {
        PageInfoRes pageInfoRes = new PageInfoRes();
        // 开始mybatis分页
        PageHelper.startPage(pageNum, pageSize);
        // 获取selectAll结果
        List<User> users = userMapper.selectAll(username, nickname);
        // 获取该分页结果
        PageInfo<User> pageInfo = new PageInfo<>(users);

        List<User> stringDateUserResList = new ArrayList<>(pageInfo.getList());
        // 封装PageInfoRes对象
        pageInfoRes.setRecords(stringDateUserResList);
        pageInfoRes.setPages(pageInfo.getPages());
        pageInfoRes.setSize(pageInfo.getSize());
        pageInfoRes.setTotal(pageInfo.getTotal());
        pageInfoRes.setSearchCount(true);
        // 返回
        return pageInfoRes;
    }

    @Override
    public String selectPassword(Integer userId) {
        return userMapper.selectPassword(userId);
    }

    @Override
    public int updatePassword(Integer userId, String newPassword) {
        return userMapper.updatePassword(userId, newPassword);
    }

    @Override
    public int updatePasswordByMobile(String mobile, String password) {
        return userMapper.updatePasswordByMobile(mobile, password);
    }

    @Override
    public int updateStatus(Integer userId, Integer status) {
        return userMapper.updateStatus(userId, status);
    }

    @Override
    public int bindUsername(Integer userId, String username) {
        return userMapper.bindUsername(userId, username);
    }

    @Override
    public Integer selectIdByAccount(String account) {
        return userMapper.selectIdByAccount(account);
    }

    @Override
    public Integer selectIsAdmin(Integer userId) {
        return userMapper.selectIsAdmin(userId);
    }

    @Override
    public String selectPasswordByAccount(String account) {
        return userMapper.selectPasswordByAccount(account);
    }


}

