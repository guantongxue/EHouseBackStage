package com.minjiang.ehouse.entities.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @auther guannw
 * @create 2021/3/23 0:10
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseRelease {

    private String id;
    private String username;
    private String biz_type;
    private String resouce_title;
    private String resouce_desc;
    private String city;
    private String area;
    private String house_type;
    private Long price;
    private Date create_time;
    private String key_word;
    private Boolean status;
}
