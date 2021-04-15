package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.chat.GetAllMessageForm;
import com.minjiang.ehouse.entities.house.HouseCollectionForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @auther guannw
 * @create 2021/4/12 8:45
 */

@Component
@FeignClient(value = "EHOUSE-NETTY-CHAT-SERVICE")
public interface ChatMessageService {

    @PostMapping(value = "/chat/getAllChatMessage")
    public Result getAllChatMessage(@RequestBody GetAllMessageForm getAllMessageForm);

}
