package com.kabaiye.service;


import com.kabaiye.entity.vo.user.PageInfoRes;
import com.kabaiye.entity.pojo.User;

public interface UserService {

    /**
     * 保存用户、邮箱、code到redis
     * @param userId 用户id
     * @param email 邮箱地址
     * @param code 验证code
     * @return 成功信息1成功，-1/0失败
     */
    Integer VeMail(Integer userId, String email, String code);

    /**
     * 更新用户头像byId
     */
    int updateAvatar(Integer userId, String fileUri);

    /**
     * 更改用户手机号byId
     */
    int updateMobile(Integer userId, String mobile);

    /**
     * 更改用户邮箱byId
     */
    int bindMail(String userId, String email);

    /**
     * 添加用户
     */
    Integer addUser(User user);

    /**
     * 修改用户信息
     */
    int updateUser(User user);

    /**
     * 查询用户信息
     */
    User selectUser(Integer userId);

    /**
     * 分页查询用户列表
     * @param username 可选的模糊查询条件
     * @param nickname 可选的模糊查询条件
     */
    PageInfoRes selectAll(Integer pageNum, Integer pageSize, String username, String nickname);

    /**
     * 查询密码
     */
    String selectPassword(Integer userId);


    /**
     * 修改密码ById
     */
    int updatePassword(Integer userId, String newPassword);

    /**
     * 重置用户密码，根据电话修改
     */
    int updatePasswordByMobile(String mobile, String password);

    /**
     * 修改用户状态，0:正常，1:锁定，2:禁用，3:过期
     */
    int updateStatus(Integer userId, Integer status);

    /**
     * 绑定用户名
     */
    int bindUsername(Integer userId, String username);

    /**
     * 按用户名或手机号查询用户id
     */
    Integer selectIdByAccount(String account);

    /**
     * 查询用户是否是管理员
     */
    Integer selectIsAdmin(Integer userId);

    /**
     * 根据用户名或手机号查询密码
     */
    String selectPasswordByAccount(String account);
}
