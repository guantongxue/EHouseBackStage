package com.minjiang.ehouse.controller;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.chat.GetAllMessageForm;
import com.minjiang.ehouse.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @auther guannw
 * @create 2021/4/12 8:44
 */

@RestController
@Slf4j
public class ChatMessageController {


    @Autowired
    private ChatMessageService chatMessageService;

    @PostMapping(value = "/chat/getAllChatMessage")
    public Result getAllChatMessage(@RequestBody GetAllMessageForm getAllMessageForm) {
        return chatMessageService.getAllChatMessage(getAllMessageForm);
    }

}
