package com.minjiang.ehouse.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据响应对象
 *    {
 *      success ：是否成功
 *      code    ：返回码
 *      message ：返回信息
 *      //返回数据
 *      data：  ：{
 *
 *      }
 *    }
 */

@Data
@NoArgsConstructor
public class Result {
    private boolean success;//是否成功
    private Integer code;//返回码
    private String message;//返回信息
    private Object data;

    public Result(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public Result(Integer code, String message, boolean success, String token) {
        this.code = code;
        this.message = message;
        this.success = success;
    }



}
