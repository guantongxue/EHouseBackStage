package com.minjiang.ehouse.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther guannw
 * @create 2021/3/3 6:20
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    //用户id
    private String id;
    //用户名
    private String username;
    //用户密码
    private String password;
    //用户手机号码
    private String tel_phone;
    //用户身份证号码
    private String id_card;
    //用户个人描述
    private String user_desc;
    //用户权限  客服，管理员，用户，企业用户
    private String authority;

}
