package com.minjiang.ehouse.dao;

import com.minjiang.ehouse.dto.*;
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

}
