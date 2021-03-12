package com.minjiang.ehouse.controller;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.service.MapPositionCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther guannw
 * @create 2021/3/12 3:42
 */

@RestController
public class MapPostionController {

    @Resource
    private MapPositionCityService mapPositionCityService;

    private Result result;

    @GetMapping("/getPositionCity")
    public Result getPositionCity(){
        result = mapPositionCityService.getPositionCity();
        return result;
    }
}
