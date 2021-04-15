package com.minjiang.ehouse.controller;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.chat.GetAllMessageForm;
import com.minjiang.ehouse.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther guannw
 * @create 2021/4/12 8:29
 */

@RestController
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @PostMapping(value = "/chat/getAllChatMessage")
    public Result getAllChatMessage(@RequestBody GetAllMessageForm getAllMessageForm){
        return chatMessageService.getAllChatMessage(getAllMessageForm);
    }
}
