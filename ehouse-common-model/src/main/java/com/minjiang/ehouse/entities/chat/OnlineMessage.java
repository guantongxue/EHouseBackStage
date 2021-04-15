package com.minjiang.ehouse.entities.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dominick Li
 * @createTime 2020/3/4 19:58
 * @description socket接消息实体
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineMessage {

    /**
     * 消息发送者id
     */
    private String sendId;
    /**
     * 消息接受者id
     */
    private String acceptId;
    /**
     * 消息内容
     */
    private String message;

    /**
     * 头像
     */
    private String headImg;

}
