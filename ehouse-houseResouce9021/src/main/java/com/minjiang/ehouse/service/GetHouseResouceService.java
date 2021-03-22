package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.house.HouseReleaseForm;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @auther guannw
 * @create 2021/3/21 15:20
 */
@Component
public interface GetHouseResouceService {

    //获得所有城市区域选项
    public Result getCityArea();

    //获取房型
    public Result getHouseType();

    //获取所有环境
    public Result getEnvironment();

    //发布房源
    public Result houseRelease(HouseReleaseForm houseReleaseForm, MultipartFile file,  MultipartFile[] files) throws IOException;
}