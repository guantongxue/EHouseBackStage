package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.chat.GetAllMessageForm;
import org.springframework.stereotype.Component;

/**
 * @auther guannw
 * @create 2021/4/12 8:33
 */

@Component
public interface ChatMessageService {

    public Result getAllChatMessage(GetAllMessageForm getAllMessageForm);
}
