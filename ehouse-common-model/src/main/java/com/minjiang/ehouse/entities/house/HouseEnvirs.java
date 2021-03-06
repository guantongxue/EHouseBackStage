package com.minjiang.ehouse.entities.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/23 0:33
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HouseEnvirs {

    private String id;
    private String envir_name;
    private String release_id;
    private String username;

}
