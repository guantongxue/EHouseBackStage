package com.minjiang.ehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/21 15:45
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    private Long id;
    private Long city_id;
    private String area_name;
}
