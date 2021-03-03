package com.minjiang.ehouse.service;

import com.minjiang.ehouse.dao.UserDao;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.ResultCode;
import com.minjiang.ehouse.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @auther guannw
 * @create 2021/3/1 23:04
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserDao userDao;

    public Result userLogin(User user) {
        User user1 = new User();
        com.minjiang.ehouse.entities.Result result;
        user1  = userDao.selectUserByName(user);
        if(user1.getPassword().equals(user.getPassword())){
            result = new com.minjiang.ehouse.entities.Result(ResultCode.SUCCESS);
        }else {
            result = new com.minjiang.ehouse.entities.Result(ResultCode.PASSWORD_ERROR);
        }
        return result;
    }
}
