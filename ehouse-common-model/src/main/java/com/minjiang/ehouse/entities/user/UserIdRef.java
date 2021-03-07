package com.minjiang.ehouse.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther guannw
 * @create 2021/3/3 6:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdRef  {

    //用户名
    private String username;
    //用户绑定id
    private String idRef;
}
