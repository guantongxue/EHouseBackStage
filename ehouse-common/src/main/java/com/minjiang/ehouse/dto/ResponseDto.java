package com.minjiang.ehouse.dto;

/**
 * @auther guannw
 * @create 2021/3/4 22:10
 */
public class ResponseDto<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * token
     */
    private String token;

    /**
     * 刷新token
     */
    private String refreshToken;

    public ResponseDto(){
    }

    public ResponseDto(Integer code, String message, T data){
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResponseDto(Integer code, String message, String token,String refreshToken){
        this.code = code;
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public ResponseDto(Integer code, String message, String token,String refreshToken,T data){
        this.code = code;
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
        this.data = data;
    }

}
