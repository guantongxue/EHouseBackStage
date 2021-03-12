package com.minjiang.ehouse.service;

import com.minjiang.ehouse.dao.UserDao;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.ResultCode;
import com.minjiang.ehouse.entities.user.User;
import com.minjiang.ehouse.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @auther guannw
 * @create 2021/3/1 23:04
 */
@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserDao userDao;

    private Result result;


    public Result userLogin(User user) {
        User user1 ;
        user1  = userDao.selectUserByName(user);
        if(user1.getPassword()!=null){
            if(user1.getPassword().equals(user.getPassword())){
                result = new com.minjiang.ehouse.entities.Result(ResultCode.SUCCESS);
            }else {
                result = new com.minjiang.ehouse.entities.Result(ResultCode.PASSWORD_ERROR);
            }
        }else {
            result = new com.minjiang.ehouse.entities.Result(ResultCode.PASSWORD_ERROR);
        }

        return result;
    }


    @Transactional
    public Result userRegister(User user) {

        int flag = userDao.insertUser(user);
        if(flag == 1){
            log.info("注册成功，用户名："+user.getUsername());
            result = new Result(ResultCode.SUCCESS);
        }else{
            log.info("注册失败，用户名："+user.getUsername());
            result = new Result(ResultCode.REGISTER_FAIL);
        }
        return result;
    }

    public Result duplicateName(User user) {
        List<User> users = userDao.duplicateName(user.getUsername());
        if(users.size()>0){
            result = new Result(500,"用户名已存在",false);
        }else {
            result = new Result(200,"用户名可用",true);
        }
        return result;
    }
}
