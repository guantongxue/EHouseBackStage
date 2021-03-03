package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.user.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @auther guannw
 * @create 2021/3/1 23:04
 */

@Component
public interface UserLoginService {

    public Result userLogin(User user);
}
