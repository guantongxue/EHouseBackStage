package com.minjiang.ehouse.dto;

import com.minjiang.ehouse.entities.house.ReleasePhotos;
import com.minjiang.ehouse.entities.house.ReleaseVideo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @auther guannw
 * @create 2021/3/25 1:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseResouceList {

    private String id;
    private String username;
    private String city;
    private String area;
    private String house_type;
    private String resouce_title;
    private String resouce_desc;
    private String biz_type;
    private Date create_time;
    private Long price;
    private Long total;
    private List<HouseEnvironment> houseEnvironmentList;
    private List<ReleasePhotos> releasePhotosList;
    private List<ReleaseVideo> releaseVideoList;


}
