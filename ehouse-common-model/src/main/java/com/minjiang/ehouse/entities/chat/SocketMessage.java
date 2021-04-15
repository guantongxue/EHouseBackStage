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
@AllArgsConstructor
@NoArgsConstructor
public class SocketMessage {

    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 消息发送者id
     */
    private String  userId;
    /**
     * 消息接受者id或群聊id
     */
    private String chatId;
    /**
     * 消息内容
     */
    private String message;
    /*
    * 消息是否已读
    * */
    private Boolean status;

}
