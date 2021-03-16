package com.minjiang.ehouse.service;

import com.minjiang.ehouse.dao.UserDao;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.ResultCode;
import com.minjiang.ehouse.entities.user.ChangePwdForm;
import com.minjiang.ehouse.entities.user.ChangeUserInfoForm;
import com.minjiang.ehouse.entities.user.User;
import com.minjiang.ehouse.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Result selectUserDetail(User user) {
        try {
            User userTemp = userDao.selectUseDetail(user);
            if(userTemp != null){
                result = new Result(200,"查询成功",true);
                result.setData(userTemp);
            }else {
                result = new Result(201,"用户查询信息为空",true);
            }
        }catch (Exception e){
            return result = new Result(500,"用户信息查询失败",false);
        }
        return result;
    }

    @Transactional
    public Result changepwd(ChangePwdForm changePwdForm){
        User userform = new User();
        userform.setUsername(changePwdForm.getUsername());
        User user1 ;
        user1  = userDao.selectUseDetail(userform);
        if(user1 !=null){
            if(!user1.getPassword().equals(changePwdForm.getOldpwd())){
                //原密码错误
                 result = new Result(500,"原密码错误",false);
            }else {
                //密码正确
                int flag= userDao.updateUserPwd(changePwdForm);
                if (flag >0){
                    //修改密码成功
                    result = new Result(200,"修改密码成功",true);
                }else {
                    //修改密码失败
                    result = new Result(500,"修改密码失败",false);
                }
            }
        }else {
             result = new Result(500,"该用户不存在",false);
        }

        return result;
    }

    @Transactional
    public Result changeUserInfo( ChangeUserInfoForm changeUserInfoForm){
        User userform = new User();
        userform.setUsername(changeUserInfoForm.getUsername());
        User user1 ;
        user1  = userDao.selectUseDetail(userform);
        if(user1 ==null){
            result = new Result(500,"该用户不存在",false);
        }else {
            int flag = userDao.changeUserInfo(changeUserInfoForm);
            if(flag >0){
                result = new Result(200,"修改成功",true);
            }else {
                result = new Result(500,"修改失败",true);
            }
        }
        return result;
    }
}
