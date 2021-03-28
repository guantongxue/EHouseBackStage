package com.minjiang.ehouse.entities.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @auther guannw
 * @create 2021/3/25 1:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectHouseOption {
    private String city;
    private String area;
    private String house_type;
    private String  biz_type;
    private List<String> house_envirs;
    private Long minPrice;
    private Long maxPrice;
    private int price_order;
    private Long current_page;
    private Long page_size;
    private String key_word;
}
