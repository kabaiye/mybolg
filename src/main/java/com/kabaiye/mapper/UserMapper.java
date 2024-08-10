package com.kabaiye.mapper;

import com.kabaiye.entity.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 修改用户头像
     */
    @Update("update user set avatar=#{fileUri} where id=#{userId}")
    int updateAvatar(@Param("userId") Integer userId,@Param("fileUri") String fileUri);

    /**
     * 修改用户手机号
     * @return 变化的行记录数
     */
    @Update("update user set mobile=#{mobile} where id=#{userId}")
    int updateMobile(@Param("userId") Integer userId, @Param("mobile") String mobile);

    /**
     * 修改用户邮箱
     * @return 变化的行记录数
     */
    @Update("update user set email=#{email} where id=#{userId}")
    int bindMail(@Param("userId") String userId,@Param("email") String email);

    /**
     * 添加用户（注册），都是非管理员
     * @return 用户id
     */
    int addUser(User user);

    /**
     * 修改用户基本信息，不包括用户名密码等注册字段
     * "birthday": "生日",
     * "brief": "简介",
     * "gender": "性别，1：男，0：女",
     * "nickname": "昵称",
     * "userId": "当前用户id"
     */
    int updateUser(User user);

    /**
     * 查询用户详细信息,除了密码
     */
    @Select("SELECT id userId,username,password, mobile, nickname, gender," +
            "       birthday, email, brief, avatar, status," +
            "       admin AS admin, create_time AS createTime " +
            "FROM user WHERE id=#{userId}")
    User selectUser(@Param("userId") Integer userId);

    /**
     * 动态模糊查询
     */
    List<User> selectAll(@Param("username") String username,@Param("nickname") String nickname);

    /**
     * 查询密码
     */
    @Select("select password from user where id=#{userId}")
    String selectPassword(@Param("userId") Integer userId);

    /**
     * 修改密码
     */
    @Update("update user set password=#{newPassword} where id=#{userId}")
    int updatePassword(@Param("userId") Integer userId,@Param("newPassword") String newPassword);

    /**
     * 根据电话号码修改密码
     */
    @Update("update user set password=#{newPassword} where mobile=#{mobile}")
    int updatePasswordByMobile(@Param("mobile") String mobile,@Param("newPassword") String newPassword);

    /**
     * 修改用户状态，0:正常，1:锁定，2:禁用，3:过期
     */
    @Update("update user set status=#{status} where id=#{userId}")
    int updateStatus(@Param("userId") Integer userId,@Param("status") Integer status);

    /**
     * 绑定用户名
     */
    @Update("update user set username=#{username} where id=#{userId}")
    int bindUsername(@Param("userId") Integer userId,@Param("username") String username);

    /**
     * 按用户名或手机号查询用户id
     * @param account 用户名或手机号
     * @return 用户Id
     */
    @Select("select id from user where username=#{account} or mobile=#{account}")
    Integer selectIdByAccount(@Param("account") String account);

    /**
     * 查询用户是否是管理员 0不是 1是
     */
    @Select("select admin from user where id=#{userId}")
    Integer selectIsAdmin(@Param("userId") Integer userId);

    /**
     * 根据用户名或手机号查询用户密码
     * @return 密码
     */
    @Select("select password from user where username=#{account} or mobile=#{account}")
    String selectPasswordByAccount(@Param("account") String account);
}
