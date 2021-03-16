package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.user.ChangePwdForm;
import com.minjiang.ehouse.entities.user.ChangeUserInfoForm;
import com.minjiang.ehouse.entities.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @auther guannw
 * @create 2021/3/3 6:47
 */
@Component
@FeignClient(value = "EHOUSE-USER-PROVIDER-SERVICE")
public interface UserService {

    @PostMapping(value = "/user/login")
    public Result userLogin(@RequestBody User user);

    @PostMapping(value = "/user/register")
    public Result userRegister(@RequestBody User user);

    @PostMapping(value = "/user/duplicateName")
    public Result duplicateName(@RequestBody User user);

    @PostMapping(value = "/user/selectUseDetail")
    public Result selectUserDetail(@RequestBody User user);

    @PostMapping(value = "/user/changepwd")
    public Result changepwd(@RequestBody ChangePwdForm changePwdForm);

    @PostMapping(value = "/user/changeUserInfo")
    public Result changeUserInfo(@RequestBody ChangeUserInfoForm changeUserInfoForm);

}
