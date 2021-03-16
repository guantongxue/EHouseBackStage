package com.minjiang.ehouse.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.logging.StreamHandler;

/**
 * @auther guannw
 * @create 2021/3/15 20:59
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserInfoForm {

    private String username;

    private String userTel;

    private String userDesc;

    private String auth;
}
