package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.house.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    public Result houseRelease(HouseReleaseForm houseReleaseForm, MultipartFile file, MultipartFile[] files) throws IOException;

    //根据城市获取区域
    public Result getAllAreaByCity(GetAllAreaByCity getAllAreaByCity);

    //查询所有可选择城市
    public Result getAllCity();

    //根据条件分页查询房源
    public Result selectHouseByOption(SelectHouseOption selectHouseOption);

    //根据id查询房源信息
    public Result getHouseResouceById(GetHouseResouceForm getHouseResouceForm);

    //收藏
    public Result houseIsCollection(HouseCollectionForm houseCollectionForm);

    //取消收藏
    public Result cancleCollection(HouseCollectionForm houseCollectionForm);

    //获取收藏状态
    public Result getCollectionStatus(HouseCollectionForm houseCollectionForm);

    //查询获取自己发布的房源信息
    public Result getMyReleaseHouse(HouseMyReleaseForm houseMyReleaseForm);

    //删除房源
    public Result delMyReleaseHouse(DeleteHouseReleaseForm deleteHouseReleaseForm);

    //查找我们个人收藏的房源信息
    public Result selectMyCollection(SelectMyCollectionForm selectMyCollectionForm);

    //修改已经发布房源信息
    public Result editHouseRelease(HouseReleaseForm houseReleaseForm, MultipartFile file, MultipartFile[] files) throws IOException;

}