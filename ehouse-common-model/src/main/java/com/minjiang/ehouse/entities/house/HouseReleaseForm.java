package com.minjiang.ehouse.entities.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @auther guannw
 * @create 2021/3/21 17:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseReleaseForm {

    //用户姓名
    private String username;
    //业务类型
    private String bizType;
    //房源标题
    private String house_title;
    //房源描述
    private String house_desc;
    //房源城市
    private String house_city;
    //房源区域
    private String house_area;
    //房源环境
    private List<String> house_envir;
    //房源价格
    private Long house_price;
    //房源房型
    private String house_type;
    //已经发布房源信息主键id
    private String release_id;
    //已经发布图片地址
    private List<String> release_photos;
    //已经发布视频地址
    private String video_addr;


}
