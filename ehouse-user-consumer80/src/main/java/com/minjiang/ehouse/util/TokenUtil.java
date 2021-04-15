package com.minjiang.ehouse.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @auther guannw
 * @create 2021/3/4 21:38
 */
@Component
@PropertySource(value = "classpath:jwt.properties")
public class TokenUtil {
    @Value("${token.expire.time}")
    private long tokenExpireTime;

    @Value("${refresh.token.expire.time}")
    private long refreshTokenExpireTime;

    private Map<String , String> map = new HashMap<String, String>();

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 固定的头
     */
    private static final String USER = "USER";
    private static final String QIYE = "QIYE";
    private static final String KEFU = "KEFU";

    private Result result;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成token和refreshToken
     * @param username
     * @param type
     * @return
     */
    public Map<String, String> getToken(String username, String type){
        //生成refreshToken
        String refreshToken = UUID.randomUUID().toString().replaceAll("-","");
        String prefix = this.getPrefix(type);
        String token = this.buildJWT(username, prefix);
        String key = SecureUtil.md5(prefix + username);
        //向hash中放入数值
        stringRedisTemplate.opsForHash().put(key,"token", token);
        stringRedisTemplate.opsForHash().put(key,"refreshToken", refreshToken);
        //设置key过期时间
        stringRedisTemplate.expire(key,
                refreshTokenExpireTime, TimeUnit.MILLISECONDS);
        map.put("token", token);
        map.put("refreshToken", refreshToken);
        return map;
    }

    /**
     * 刷新token
     * @param username
     * @param type
     * @param refreshToken
     * @return
     */
    public Result refreshToken(String username, String type, String refreshToken){
        String prefix = this.getPrefix(type);
        String key = SecureUtil.md5(prefix + username);
        String oldRefresh = (String) stringRedisTemplate.opsForHash().get(key, "refreshToken");
        //refreshtoken过期了
        if (StrUtil.isBlank(oldRefresh)){
            result = new Result(ResultCode.REFRESH_TOKEN_TIME_OUT);
        }else {
            //新旧refresh是否相等
            if (!oldRefresh.equals(refreshToken)){
                result = new Result(ResultCode.REFRESH_TOKEN_ERROR);
                System.out.println("refreshToken错误");
            }else {
                //相等
                String token = this.buildJWT(username, prefix);
                stringRedisTemplate.opsForHash().put(key,"token", token);
                stringRedisTemplate.opsForHash().put(key,"refreshToken", refreshToken);
                stringRedisTemplate.expire(key,
                        refreshTokenExpireTime, TimeUnit.MILLISECONDS);
                result = new Result(ResultCode.SUCCESS);
                result.setToken(token);
                result.setRefreshToken(refreshToken);
            }
        }
        return result;
    }

    /**
     * 删除key
     * @param username
     * @param type
     */
    public boolean removeToken(String username, String type){
        String prefix = this.getPrefix(type);
        String key = SecureUtil.md5(prefix + username);
        return stringRedisTemplate.delete(key);
    }

    /**
     * 获取前缀
     * @param type 1 pc端  2  暂未定义 3 暂未定义
     * @return
     */
    private String getPrefix(String type){
        String prefix = null;
        if ("1".equals(type)){
            prefix =USER;
        }else if ("2".equals(type)){
            prefix = QIYE;
        }else if ("3".equals(type)){
            prefix =KEFU;
        }
        return prefix;
    }

    /**
     * 生成jwt
     * @param username 用户名
     * @param prefix 前缀
     * @return
     */
    private String buildJWT(String username, String prefix){
        //生成jwt
        Date now = new Date();
        Algorithm algo = Algorithm.HMAC256(prefix);
        String token = JWT.create()
                //签发人
                .withIssuer("userName")
                //签发时间
                .withIssuedAt(now)
                //过期时间
                .withExpiresAt(new Date(now.getTime() + tokenExpireTime))
                //自定义的存放的数据
                .withClaim("username", username)
                //签名
                .sign(algo);
        return token;
    }

}
