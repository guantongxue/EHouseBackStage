package com.minjiang.ehouse.entities.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/12 3:47
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionCity {

    private String adcode;
    private String city;
    private String info;
    private String infocode;
    private String province;
    private String rectangle;
    private String status;
}
