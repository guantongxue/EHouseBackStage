package com.minjiang.ehouse.controller;

import cn.hutool.core.util.StrUtil;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.ResultCode;
import com.minjiang.ehouse.entities.user.User;
import com.minjiang.ehouse.service.UserService;
import com.minjiang.ehouse.util.IdWorker;
import com.minjiang.ehouse.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther guannw
 * @create 2021/3/3 6:04
 */

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private Result result;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private IdWorker idWorker;


    @PostMapping("/user/login")
    public Result userLogin(@RequestBody User user){
//        Result result = userService.userLogin(user)
        if(StrUtil.isNotEmpty(user.getUsername())&& StrUtil.isNotEmpty(user.getPassword())){
             result = userService.userLogin(user);
            if(result.getCode() == 200){
                //密码正确
                Map<String,String> map = tokenUtil.getToken(user.getUsername(),"1");
                //返回结果
                result = new Result(ResultCode.SUCCESS);
                result.setToken(map.get("token"));
                result.setRefreshToken(map.get("refreshToken"));
                Map<String,String> userInfo = new HashMap<String, String>();
                userInfo.put("username",user.getUsername());
                result.setData(userInfo);
            }else{
                //密码错误
                 result = new Result(ResultCode.PASSWORD_ERROR);
            }
        }else {
            //用户名和密码为空
             result = new Result(ResultCode.USER_INFO_NULL);
        }
        return result ;
    }

    @PostMapping("/user/register")
    public Result userRegister(@RequestBody User user){
        result = userService.duplicateName(user);
        //如果重名直接返回
        if( result.getCode() != 200){
            return result;
        }
        String id = idWorker.nextId()+"";
        user.setId(id);
        result = userService.userRegister(user);
        if(result.getCode() == 200){
            Map<String,String> map = tokenUtil.getToken(user.getUsername(),"1");
            //返回结果
            result.setToken(map.get("token"));
            result.setRefreshToken(map.get("refreshToken"));
            Map<String,String> userInfo = new HashMap<String, String>();
            userInfo.put("username",user.getUsername());
            result.setData(userInfo);
        }
        return result;
    }

    @PostMapping(value = "/user/duplicateName")
    public Result DuplicateName(@RequestBody User user){
        result  = userService.duplicateName(user);
        return  result;
    }
}
