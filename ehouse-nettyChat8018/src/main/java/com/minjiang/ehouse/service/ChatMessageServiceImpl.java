package com.minjiang.ehouse.service;

import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.ResultCode;
import com.minjiang.ehouse.entities.chat.GetAllMessageForm;
import com.minjiang.ehouse.entities.chat.SocketMessage;
import com.minjiang.ehouse.util.RedisOperatingUtil;
import com.minjiang.ehouse.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther guannw
 * @create 2021/4/12 8:35
 */

@Service
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private RedisOperatingUtil redisOperatingUtil;

    private static  final String keyHead = "chat&&";

    @Override
    public Result getAllChatMessage(GetAllMessageForm getAllMessageForm) {
        String key =keyHead+getAllMessageForm.getUserId();
        //从redis中获取聊天数据
        List<SocketMessage> socketMessageList = (List<SocketMessage>) redisOperatingUtil.listRange(key,0,-1);
        log.info("redis数据.+"+socketMessageList);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(socketMessageList);
        return result;
    }

}
