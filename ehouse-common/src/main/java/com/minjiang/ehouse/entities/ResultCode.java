package com.minjiang.ehouse.entities;
/**
 * 公共的返回码
 *  返回码code:
 *      成功：10000
 *      失败：10001
 *      未登录：10002
 *      未授权：10003
 *      抛出异常：99999
 * */
public enum ResultCode {

    SUCCESS(true,200,"操作成功！"),
    FAIL(false,500,"操作失败"),
    UNAUTHENTICATED(false,400,"您还未登录"),
    UNAUTHORISE(false,401,"权限不足"),
    SERVER_ERROR(false,1000,"抱歉，系统繁忙，请稍后重试！"),
    PASSWORD_ERROR(false,501,"密码错误");
    //---用户操作返回码 2xxxx---
    //---企业操作返回码 3xxxx---
    //---权限操作返回码---
    //---其他操作返回码---

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;

    }
    public boolean success(){
        return success;
    }

    public int code(){
        return code;
    }

    public String message(){
        return message;
    }

}
