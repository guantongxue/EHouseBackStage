package com.minjiang.ehouse.entities.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/3/28 17:57
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseCollectionForm {

    private String id;

    private String username;

    private String release_id;

    private Boolean isCollection;

}
