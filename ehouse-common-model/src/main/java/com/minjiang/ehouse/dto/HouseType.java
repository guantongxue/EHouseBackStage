package com.minjiang.ehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/21 16:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseType {

    private Long id;
    private String house_type;
}
