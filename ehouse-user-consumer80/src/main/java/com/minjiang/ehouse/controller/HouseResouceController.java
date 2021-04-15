package com.minjiang.ehouse.controller;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.house.*;
import com.minjiang.ehouse.service.HouseResouceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @auther guannw
 * @create 2021/3/21 16:03
 */
@RestController
@Slf4j
public class HouseResouceController {

    private Result result;

    @Autowired
    private HouseResouceService houseResouceService;

    @GetMapping(value = "/house/getCityArea")
    public Result getCityArea(){
        return houseResouceService.getCityArea();
    }

    @GetMapping(value = "/house/getHouseType")
    public Result getHouseType(){
        return houseResouceService.getHouseType();
    }

    @GetMapping(value = "/house/getEnvironment")
    public Result getEnvironment(){
        return houseResouceService.getEnvironment();
    }

    @PostMapping(value = "/house/release")
    @ResponseBody
    public Result houseRelease(String houseReleaseForm, @RequestParam(value = "fileVideo",required=false) MultipartFile file, @RequestParam(value = "filePhotos",required=false) MultipartFile[] files)throws IOException {
        return houseResouceService.houseRelease(houseReleaseForm,file,files);
    }
    @PostMapping(value = "/house/getAllAreaByCity")
    public Result getAllAreaByCity(@RequestBody GetAllAreaByCity getAllAreaByCity){
        return houseResouceService.getAllAreaByCity(getAllAreaByCity);
    }

    @GetMapping(value = "/house/getAllCity")
    public Result getAllCity(){
        return houseResouceService.getAllCity();
    }

    @PostMapping(value = "/house/selectHouseByOption")
    public Result selectHouseByOption(@RequestBody SelectHouseOption selectHouseOption){
        return houseResouceService.selectHouseByOption(selectHouseOption);
    }
    @PostMapping(value = "/house/getHouseResouceById")
    public Result getHouseResouceById(@RequestBody GetHouseResouceForm getHouseResouceForm){
        return houseResouceService.getHouseResouceById(getHouseResouceForm);
    }
    //收藏
    @PostMapping(value = "/house/houseIsCollection")
    public Result houseIsCollection(@RequestBody HouseCollectionForm houseCollectionForm){
        return houseResouceService.houseIsCollection(houseCollectionForm);
    }
    //取消收藏
    @PostMapping(value = "/house/cancleCollection")
    public Result cancleCollection(@RequestBody HouseCollectionForm houseCollectionForm){
        return houseResouceService.cancleCollection(houseCollectionForm);
    }
    //获取收藏状态
    @PostMapping(value = "/house/getCollectionStatus")
    public Result getCollectionStatus(@RequestBody HouseCollectionForm houseCollectionForm){
        return houseResouceService.getCollectionStatus(houseCollectionForm);
    }
    //查询获取自己发布的房源信息
    @PostMapping(value = "/house/getMyReleaseHouse")
    public Result getMyReleaseHouse(@RequestBody HouseMyReleaseForm houseMyReleaseForm){
        return houseResouceService.getMyReleaseHouse(houseMyReleaseForm);
    }
    @PostMapping(value = "/house/delMyReleaseHouse")
    public Result delMyReleaseHouse(@RequestBody DeleteHouseReleaseForm deleteHouseReleaseForm){
        return houseResouceService.delMyReleaseHouse(deleteHouseReleaseForm);
    }

    //查询个人收藏的房源信息
    @PostMapping(value = "/house/selectMyCollection")
    public Result selectMyCollection( @RequestBody SelectMyCollectionForm selectMyCollectionForm){
        return houseResouceService.selectMyCollection(selectMyCollectionForm);
    }

    @PostMapping(value = "/house/editHouseRelease")
    @ResponseBody
    public Result editHouseRelease(String houseReleaseForm, @RequestParam(value = "fileVideo",required=false) MultipartFile file, @RequestParam(value = "filePhotos",required=false) MultipartFile[] files)throws IOException {
        return houseResouceService.editHouseRelease(houseReleaseForm,file,files);
    }
}
