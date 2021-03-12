package com.minjiang.ehouse.controller;

import com.minjiang.ehouse.entities.Result;
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
}
