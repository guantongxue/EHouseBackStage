package com.minjiang.ehouse.controller;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.user.User;
import com.minjiang.ehouse.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther guannw
 * @create 2021/3/3 6:04
 */

@RestController
@Slf4j
public class UserController {

    @Resource
    private UserLoginService userLoginService;

    @PostMapping(value = "/user/login")
    public Result userLogin(@RequestBody User user) {
        Result result = new Result();
        result = userLoginService.userLogin(user);
        return result;
    }
}
