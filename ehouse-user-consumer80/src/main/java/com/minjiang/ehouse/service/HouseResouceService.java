package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.house.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "/house/getAllAreaByCity")
    public Result getAllAreaByCity(@RequestBody GetAllAreaByCity getAllAreaByCity);

    @GetMapping(value = "/house/getAllCity")
    public Result getAllCity();

    @PostMapping(value = "/house/selectHouseByOption")
    public Result selectHouseByOption(@RequestBody SelectHouseOption selectHouseOption);

    @PostMapping(value = "/house/getHouseResouceById")
    public Result getHouseResouceById(@RequestBody GetHouseResouceForm getHouseResouceForm);

    @PostMapping(value = "/house/houseIsCollection")
    public Result houseIsCollection(@RequestBody HouseCollectionForm houseCollectionForm);

    @PostMapping(value = "/house/cancleCollection")
    public Result cancleCollection(@RequestBody HouseCollectionForm houseCollectionForm);

    @PostMapping(value = "/house/getCollectionStatus")
    public Result getCollectionStatus(@RequestBody HouseCollectionForm houseCollectionForm);
}
