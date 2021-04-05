package com.minjiang.ehouse.controller;

import com.google.gson.Gson;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.house.*;
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
    @PostMapping(value = "/house/getAllAreaByCity")
    public Result getAllAreaByCity(@RequestBody GetAllAreaByCity getAllAreaByCity){
        return getHouseResouceService.getAllAreaByCity(getAllAreaByCity);
    }

   @GetMapping(value = "/house/getAllCity")
    public Result getAllCity(){
       return getHouseResouceService.getAllCity();
    }

    @PostMapping(value = "/house/selectHouseByOption")
    public Result selectHouseByOption(@RequestBody SelectHouseOption selectHouseOption) {
        return getHouseResouceService.selectHouseByOption(selectHouseOption);
    }

    @PostMapping(value = "/house/getHouseResouceById")
    public Result getHouseResouceById(@RequestBody GetHouseResouceForm getHouseResouceForm){
        return getHouseResouceService.getHouseResouceById(getHouseResouceForm);
    }
    //收藏
    @PostMapping(value = "/house/houseIsCollection")
    public Result houseIsCollection(@RequestBody HouseCollectionForm houseCollectionForm){
        return getHouseResouceService.houseIsCollection(houseCollectionForm);
    }
    //取消收藏
    @PostMapping(value = "/house/cancleCollection")
    public Result cancleCollection(@RequestBody HouseCollectionForm houseCollectionForm){
        return getHouseResouceService.cancleCollection(houseCollectionForm);
    }
    //获取收藏状态
    @PostMapping(value = "/house/getCollectionStatus")
    public Result getCollectionStatus(@RequestBody HouseCollectionForm houseCollectionForm){
        return getHouseResouceService.getCollectionStatus(houseCollectionForm);
    }
    //查询获取自己发布的房源信息
    @PostMapping(value = "/house/getMyReleaseHouse")
    public Result getMyReleaseHouse(@RequestBody HouseMyReleaseForm houseMyReleaseForm){
        return getHouseResouceService.getMyReleaseHouse(houseMyReleaseForm);
    }
    //删除房源
    @PostMapping(value = "/house/delMyReleaseHouse")
    public Result delMyReleaseHouse(@RequestBody DeleteHouseReleaseForm deleteHouseReleaseForm){
        return getHouseResouceService.delMyReleaseHouse(deleteHouseReleaseForm);
    }
    //查询个人收藏的房源信息
    @PostMapping(value = "/house/selectMyCollection")
    public Result selectMyCollection(@RequestBody SelectMyCollectionForm selectMyCollectionForm){
        return getHouseResouceService.selectMyCollection(selectMyCollectionForm);
    }

    @PostMapping(value = "/house/editHouseRelease")
    @ResponseBody
    public Result editHouseRelease(String houseReleaseForm, @RequestParam(value = "fileVideo",required=false) MultipartFile file, @RequestParam(value = "filePhotos",required=false) MultipartFile[] files)throws IOException{
        Gson gson = new Gson();
        HouseReleaseForm houseReleaseForm1 = gson.fromJson(houseReleaseForm, HouseReleaseForm.class);
        return getHouseResouceService.editHouseRelease(houseReleaseForm1,file,files);
    }

}
