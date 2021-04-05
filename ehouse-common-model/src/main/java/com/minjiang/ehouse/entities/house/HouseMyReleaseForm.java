package com.minjiang.ehouse.entities.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/30 9:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseMyReleaseForm {

    private String username;

    private int order_createTime;

    private String key_word;

    private Long current_page;

    private Long page_size;

}
