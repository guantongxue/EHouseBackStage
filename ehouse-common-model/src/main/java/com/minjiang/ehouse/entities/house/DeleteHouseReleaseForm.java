package com.minjiang.ehouse.entities.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/30 14:15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteHouseReleaseForm {

    private String release_id;

    private String username;
}
