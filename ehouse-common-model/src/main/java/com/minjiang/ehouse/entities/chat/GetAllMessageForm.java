package com.minjiang.ehouse.entities.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther guannw
 * @create 2021/4/12 8:31
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllMessageForm {

    private String userId;

    private String chatId;
}
