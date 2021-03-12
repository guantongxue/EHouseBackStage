package com.minjiang.ehouse.controller;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.user.User;
import com.minjiang.ehouse.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther guannw
 * @create 2021/3/12 4:38
 */

@RestController
public class MapPostionController {

    private Result result;

    @Autowired
    private MapService mapService;

    @GetMapping(value = "/getPositionCity")
    public Result getPositionCity() {
        return mapService.getPositionCity();
    }
}
