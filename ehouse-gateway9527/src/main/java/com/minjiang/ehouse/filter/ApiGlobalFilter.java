package com.minjiang.ehouse.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.minjiang.ehouse.exception.BizException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @auther guannw
 * @create 2021/3/4 22:21
 */
@Component
@PropertySource(value = "classpath:jwt.properties")
public class ApiGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 不进行token校验的请求地址
     */
    @Value("#{'${jwt.ignoreUrlList}'.split(',')}")
    public List<String> ignoreUrl;

    /**
     * 拦截所有的请求头
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().toString();
        boolean status = CollectionUtil.contains(ignoreUrl, requestUrl);
        if (!status){
            String token = exchange.getRequest().getHeaders().getFirst("token");
            //type用于区分不同的端，在做校验token时需要
            String type= exchange.getRequest().getHeaders().getFirst("type");
            ServerHttpResponse response = exchange.getResponse();
            //没有数据
            if (StrUtil.isBlank(token) || StrUtil.isBlank(type)) {
//                throw new BizException("500","token校验失败");
                JSONObject message = new JSONObject();
                message.put("code", 504);
                message.put("message", "鉴权失败，无token或类型");
                byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "text/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
                //有数据
            }else {
                JSONObject message = new JSONObject();
                String prefix = this.getPrefix(type);
                //校验token
                    String username = verifyJWT(token ,prefix);
                    if (StrUtil.isEmpty(username)){
//                        throw new BizException("500","token校验失败");
                        message.put("message", "token错误");
                        message.put("code", 506);
                        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
                        DataBuffer buffer = response.bufferFactory().wrap(bits);
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        response.getHeaders().add("Content-Type", "text/json;charset=UTF-8");
                        return response.writeWith(Mono.just(buffer));
                    }
                    //将现在的request，添加当前身份
                    ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("Authorization-UserName", username).build();
                    ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
                    return chain.filter(mutableExchange);

            }
        }
        return chain.filter(exchange);

    }
    /**
     * JWT验证
     * @param token
     * @return userPhone
     */
    private String verifyJWT(String token, String prefix){
        String userName;
        try {
            Algorithm algorithm = Algorithm.HMAC256(prefix);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("userName")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            userName = jwt.getClaim("username").asString();
        } catch (JWTVerificationException e){
//            e.printStackTrace();
//            throw new BizException("500","token校验失败");
            return "";
        }
        return userName;
    }
    /**
     * 根据type获取前缀
     * @param type
     * @return
     */
    private String getPrefix(String type){
        String prefix = null;
        if ("1".equals(type)){
            prefix = "USER";
        }else if ("2".equals(type)){
            prefix = "QIYE";
        }else if ("3".equals(type)){
            prefix = "KEFU";
        }
        return prefix;
    }
    public int getOrder() {
        return -200;
    }
}
