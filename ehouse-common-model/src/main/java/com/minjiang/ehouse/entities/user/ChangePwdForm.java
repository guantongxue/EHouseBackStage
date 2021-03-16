package com.minjiang.ehouse.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/15 17:56
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePwdForm {

    private String username;

    private String oldpwd;

    private String newpwd;
}
