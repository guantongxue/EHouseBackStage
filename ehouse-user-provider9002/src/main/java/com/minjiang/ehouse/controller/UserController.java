package com.minjiang.ehouse.controller;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.user.ChangePwdForm;
import com.minjiang.ehouse.entities.user.ChangeUserInfoForm;
import com.minjiang.ehouse.entities.user.User;
import com.minjiang.ehouse.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @auther guannw
 * @create 2021/3/3 6:04
 */
@Slf4j
@RestController
public class UserController {

    @Resource
    private UserLoginService userLoginService;
    private Result result;
    @PostMapping(value = "/user/login")
    public Result userLogin(@RequestBody User user) {
        result = userLoginService.userLogin(user);
        return result;
    }
    @PostMapping(value = "/user/register")
    public Result userRegister(@RequestBody User user) {
        result = userLoginService.userRegister(user);
        return result;
    }

    @PostMapping(value = "/user/duplicateName")
    public Result duplicateName(@RequestBody User user){
        result  = userLoginService.duplicateName(user);
        return  result;
    }

    @PostMapping(value = "/user/selectUseDetail")
    public Result selectUserDetail(@RequestBody User user){
        result  = userLoginService.selectUserDetail(user);
        return  result;
    }

    @PostMapping(value = "/user/changepwd")
    public Result changepwd(@RequestBody ChangePwdForm changePwdForm){
        result  = userLoginService.changepwd(changePwdForm);
        return result;
    }

    @PostMapping(value = "/user/changeUserInfo")
    public Result changeUserInfo(@RequestBody ChangeUserInfoForm changeUserInfoForm){
        result  = userLoginService.changeUserInfo(changeUserInfoForm);
        return result;
    }
}
