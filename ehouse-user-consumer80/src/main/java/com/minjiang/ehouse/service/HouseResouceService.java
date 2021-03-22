package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.house.HouseReleaseForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @auther guannw
 * @create 2021/3/21 16:04
 */
@Component
@FeignClient(value = "EHOUSE-HOUSE-RESOUCE-SERVICE")
public interface HouseResouceService {

    @GetMapping(value = "/house/getCityArea")
    public Result getCityArea();

    @GetMapping(value = "/house/getHouseType")
    public Result getHouseType();

    @GetMapping(value = "/house/getEnvironment")
    public Result getEnvironment();

    @PostMapping(value = "/house/release")
    @ResponseBody
    public Result houseRelease(String houseReleaseForm, @RequestParam(value = "fileVideo",required=false) MultipartFile file, @RequestParam(value = "filePhotos",required=false) MultipartFile[] files)throws IOException ;
}
