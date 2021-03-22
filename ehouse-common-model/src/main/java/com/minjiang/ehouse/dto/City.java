package com.minjiang.ehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.dc.pr.PRError;

import java.util.List;

/**
 * @auther guannw
 * @create 2021/3/21 15:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private Long id;

    private String city_name;

    private List<Area> areaList;



}
