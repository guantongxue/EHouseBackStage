package com.minjiang.ehouse.entities.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/23 0:42
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseVideo {

    private String id;
    private String video_addr;
    private String release_id;
    private String username;
}
