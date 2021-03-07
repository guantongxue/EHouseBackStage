package com.minjiang.ehouse.entities;

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
    /**
     * token
     */
    private String token;

    /**
     * 刷新token
     */
    private String refreshToken;

    public Result(ResultCode code) {
        this.success = success;
        this.code = code.code;
        this.message = code.message;
    }

    public Result(ResultCode code, Object data){
        this.success = success;
        this.code = code.code;
        this.message = code.message;
        this.data = data;
    }

    public Result(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public Result(Integer code, String message, boolean success,String token) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.token = token;
    }
    public Result(Integer code, String message, boolean success,String token,String refreshToken) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public static Result SUCCESS(){return new Result(ResultCode.SUCCESS);}

    public static Result ERROR(){return new Result(ResultCode.SERVER_ERROR);}

    public static Result FAIL(){return new Result(ResultCode.FAIL);}

    public static Result UNAUTHENTICATED(){
        return new Result(ResultCode.UNAUTHENTICATED);
    }

    public static Result UNAUTHORISE(){return new Result(ResultCode.UNAUTHORISE);}

    public static Result PASSWORD_ERROR(){return new Result(ResultCode.PASSWORD_ERROR);}

    public static Result REFRESH_TOKEN_TIME_OUT(){return new Result(ResultCode.REFRESH_TOKEN_TIME_OUT);}

    public static Result REFRESH_TOKEN_ERROR(){return new Result(ResultCode.REFRESH_TOKEN_ERROR);}

    public static Result USER_INFO_NULL(){return new Result(ResultCode.USER_INFO_NULL);}

    public static Result TOKEN_ERROR(){return new Result(ResultCode.TOKEN_ERROR);}

}
