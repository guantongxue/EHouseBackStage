package com.minjiang.ehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.dc.pr.PRError;

/**
 * @auther guannw
 * @create 2021/3/21 16:17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseEnvironment {

    private Long id;

    private String envir_type;
}
