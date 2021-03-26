package com.minjiang.ehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @auther guannw
 * @create 2021/3/26 4:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectHouseDTO {

    private List<HouseResouceList> houseResouceListList;

    private Integer count;

}
