package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @auther guannw
 * @create 2021/3/12 4:40
 */
@Component
@FeignClient(value = "EHOUSE-MAP-SERVICE")
public interface MapService {

    @GetMapping(value = "/getPositionCity")
    public Result getPositionCity();
}
