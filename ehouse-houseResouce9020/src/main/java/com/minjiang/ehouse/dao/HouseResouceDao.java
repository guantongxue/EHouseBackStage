package com.minjiang.ehouse.dao;

import com.minjiang.ehouse.dto.*;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.house.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther guannw
 * @create 2021/3/21 15:24
 */

@Mapper
@Component
public interface HouseResouceDao {

    //查询所有城市区域
    public List<City> getAllCityArea();
    //查询所有房型
    public List<HouseType> getAllHouseType();
    //查询所有环境
    public List<HouseEnvironment> getHouseEnvironment();
    //发布房源主表
    public int saveHouseRelease(HouseRelease houseRelease);
    //保存房源发布环境
    public int saveReleaseEnvirs(List<HouseEnvirs> envirsList);
    //保存房源发布图片信息
    public int saveReleasePhotos(List<ReleasePhotos> photosList);
    //保存房源发布视频信息
    public int saveReleaseVideo(ReleaseVideo releaseVideo);
    //根据城市名称获取区域名称
    public List<Area> getAllAreaByCity(GetAllAreaByCity getAllAreaByCity);
    //查询所有可选择城市
    public List<AllCity> getAllCity();
    //根据条件分页查询房源
    public List<Object> selectHouseByOption(SelectHouseOption selectHouseOption);
    //根据id查询房源信息
    public HouseResouceList getHouseResouceById(GetHouseResouceForm getHouseResouceForm);
    //获取收藏状态
    public HouseCollectionForm getCollectionStatus(HouseCollectionForm houseCollectionForm);
    //保存房源收藏信息
    public int saveHouseCollection(HouseCollectionForm houseCollectionForm);
    //修改房源收藏信息
    public int updateHouseCollection(HouseCollectionForm houseCollectionForm);
    //获取自己发布的房源信息
    public List<Object>  getMyReleaseHouse(HouseMyReleaseForm houseMyReleaseForm);
    //删除发布房源信息
    public int delMyReleaseHouse(DeleteHouseReleaseForm deleteHouseReleaseForm);
    //查询个人收藏的房源信息
    public List<Object>  selectMyCollection(SelectMyCollectionForm selectMyCollectionForm);

}
