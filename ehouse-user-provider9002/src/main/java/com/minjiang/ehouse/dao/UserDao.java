package com.minjiang.ehouse.dao;

import com.minjiang.ehouse.entities.user.ChangePwdForm;
import com.minjiang.ehouse.entities.user.ChangeUserInfoForm;
import com.minjiang.ehouse.entities.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther guannw
 * @create 2021/3/3 13:10
 */
@Mapper
@Component
public interface UserDao {
    //查询用户信息
    public User selectUserByName(User user);
    //注册用户
    public int  insertUser(User user);
    //根据用户名查询用户信息
    public List<User> duplicateName(String username);
    //根据用户名查询用户信息
    public User selectUseDetail(User user);
    //修改用户密码
    public int updateUserPwd(ChangePwdForm changePwdForm);
    //修改用户信息
    public int changeUserInfo(ChangeUserInfoForm changeUserInfoForm);
}
