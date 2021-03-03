package com.minjiang.ehouse.dao;

import com.minjiang.ehouse.entities.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther guannw
 * @create 2021/3/3 22:05
 */
@Mapper
public interface UserDao {
    public User selectUserByName(User user);
}
