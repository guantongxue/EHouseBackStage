package com.minjiang.ehouse.controller;

import com.google.gson.Gson;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.ResultCode;
import com.minjiang.ehouse.entities.house.HouseReleaseForm;
import com.minjiang.ehouse.service.GetHouseResouceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @auther guannw
 * @create 2021/3/21 15:17
 */

@RestController
@Slf4j
public class GetHouserRecouseController {

    @Autowired
    private GetHouseResouceService getHouseResouceService;

    @GetMapping(value = "/house/getCityArea")
    public Result getCityArea(){
        return getHouseResouceService.getCityArea();
    }

    @GetMapping(value = "/house/getHouseType")
    public Result getHouseType(){
        return getHouseResouceService.getHouseType();
    }

    @GetMapping(value = "/house/getEnvironment")
    public Result getEnvironment(){
        return getHouseResouceService.getEnvironment();
    }

    @PostMapping(value = "/house/release")
    @ResponseBody
    public Result houseRelease(String houseReleaseForm, @RequestParam(value = "fileVideo",required=false) MultipartFile file, @RequestParam(value = "filePhotos",required=false) MultipartFile[] files)throws IOException {
        Gson gson = new Gson();
        HouseReleaseForm houseReleaseForm1 = gson.fromJson(houseReleaseForm, HouseReleaseForm.class);
        return getHouseResouceService.houseRelease(houseReleaseForm1,file,files);

    }
}
