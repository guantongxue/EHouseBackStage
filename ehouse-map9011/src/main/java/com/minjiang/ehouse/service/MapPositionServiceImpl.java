package com.minjiang.ehouse.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.ResultCode;
import com.minjiang.ehouse.entities.map.PositionCity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @auther guannw
 * @create 2021/3/12 3:45
 */
@Service
@Slf4j
public class MapPositionServiceImpl implements MapPositionCityService{

    private static final  String key = "4ce136b9d07cd583aaa787e820f12d50";
    private  static final String baseURI = "http://restapi.amap.com/v3/ip?key=";

    private PositionCity positionCity;
    private Result result;

    public Result getPositionCity() {

        String res = HttpUtil.get(baseURI+key,5000);

        JSONObject jsonObject = JSONObject.parseObject(res);
        log.info("请求信息:"+jsonObject);
        positionCity = new PositionCity();
        positionCity = JSON.toJavaObject(jsonObject,PositionCity.class);
        log.info("请求信息:"+positionCity);
        if(positionCity.getInfo().equals("OK")){
            result  = new Result(ResultCode.SUCCESS);
            result.setData(positionCity);
        }else {
            result = new Result(ResultCode.FAIL);
        }

        return result;
    }
}
