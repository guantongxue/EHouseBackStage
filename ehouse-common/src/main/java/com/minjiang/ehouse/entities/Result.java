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

    public static Result SUCCESS(){return new Result(ResultCode.SUCCESS);}

    public static Result ERROR(){return new Result(ResultCode.SERVER_ERROR);}

    public static Result FAIL(){return new Result(ResultCode.FAIL);}

    public static Result UNAUTHENTICATED(){
        return new Result(ResultCode.UNAUTHENTICATED);
    }

    public static Result UNAUTHORISE(){return new Result(ResultCode.UNAUTHORISE);}

    public static Result PASSWORD_ERROR(){return new Result(ResultCode.PASSWORD_ERROR);}
}
