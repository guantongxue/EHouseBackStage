package com.minjiang.ehouse.entities.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/31 4:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectMyCollectionForm {

    private String username;
    private String key_word;
    private Long current_page;
    private Long page_size;

}
