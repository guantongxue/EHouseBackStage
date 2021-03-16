package com.minjiang.ehouse.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.minjiang.ehouse.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.netty.http.server.HttpServerRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value =BizException.class)
    @ResponseBody
    public Result bizExceptionHandler(HttpServerRequest req, BizException e){
        logger.error("自定义业务异常！原因是:",e);
        return new Result(Integer.parseInt(e.errorCode),e.errorMsg,false);
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Result exceptionHandler( HttpServerRequest req,NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return new Result(400,"请求的数据格式不符",false);
    }


    /**
     * 处理其他异常
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServerRequest req,Exception e){
        logger.error("未知异常！原因是:",e);
        return new Result(500,"服务器内部错误",false);
    }
    /**
     * token异常
     */
    @ExceptionHandler(value = TokenExpiredException.class)
    @ResponseBody
    public Result tokenExpiredException(HttpServerRequest req,TokenExpiredException e){
        logger.error("未知异常！原因是:",e);
        return new Result(500,"token失效",false);
    }
}