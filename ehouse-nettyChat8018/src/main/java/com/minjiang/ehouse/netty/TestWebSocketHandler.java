package com.minjiang.ehouse.netty;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.minjiang.ehouse.entities.chat.SocketMessage;
import com.minjiang.ehouse.repository.UserGroupRepository;
import com.minjiang.ehouse.util.RedisOperatingUtil;
import com.minjiang.ehouse.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dominick Li
 * @createTime 2020/2/28  13:07
 * @description
 **/

public class TestWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final Logger logger = LoggerFactory.getLogger(TestWebSocketHandler.class);

    @Autowired
    private RedisOperatingUtil redisOperatingUtil = SpringUtil.getBean(RedisOperatingUtil.class);

    @Resource
    private SocketMessage socketMessage;
    /**
     * 存储已经登录用户的channel对象
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存储用户id和用户的channelId绑定
     */
    public static ConcurrentHashMap<Long, ChannelId> userMap = new ConcurrentHashMap<Long, ChannelId>();
    /**
     * 用于存储群聊房间号和群聊成员的channel信息
     */
    public static ConcurrentHashMap<Long, ChannelGroup> groupMap = new ConcurrentHashMap<Long, ChannelGroup>();

    /**
     * 获取用户拥有的群聊id号
     */
    UserGroupRepository userGroupRepositor = SpringUtil.getBean(UserGroupRepository.class);

    /**
     * key值头
     * */
    private static  final String keyHead = "chat&&";
//    @Resource
//    UserGroupRepository userGroupRepositor;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("与客户端建立连接，通道开启！");
        //添加到channelGroup通道组
        channelGroup.add(ctx.channel());
        ctx.channel().id();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("与客户端断开连接，通道关闭！");
        //添加到channelGroup 通道组
        channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //首次连接是FullHttpRequest，把用户id和对应的channel对象存储起来
        if (null != msg && msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            Long userId = Long.valueOf(getUrlParams(uri));
//            userMap.put(getUrlParams(uri), ctx.channel().id());
            userMap.put(userId, ctx.channel().id());
            logger.info("登录的用户id是：{}", userId);
//            第1次登录,需要查询下当前用户是否加入过群,加入过群,则放入对应的群聊里
            List<Long> groupIds = userGroupRepositor.findGroupIdByUserId(userId);
            ChannelGroup cGroup = null;
            //查询用户拥有的组是否已经创建了
            if(groupIds != null ){
                for (Long groupId : groupIds) {
                    cGroup = groupMap.get(groupId);
                    //如果群聊管理对象没有创建
                    if (cGroup == null) {
                        //构建一个channelGroup群聊管理对象然后放入groupMap中
                        cGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                        groupMap.put(groupId, cGroup);
                    }
                    //把用户放到群聊管理对象里去
                    cGroup.add(ctx.channel());
                }
            }

            //如果url包含参数，需要处理
            if (uri.contains("?")) {
                String newUri = uri.substring(0, uri.indexOf("?"));
                request.setUri(newUri);
            }

        } else if (msg instanceof TextWebSocketFrame) {
            //正常的TEXT消息类型
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            logger.info("客户端收到服务器数据：{}", frame.text());
            SocketMessage socketMessage = JSON.parseObject(frame.text(), SocketMessage.class);
            //处理群聊任务
            if ("group".equals(socketMessage.getMessageType())) {
                //推送群聊信息
                groupMap.get(Long.valueOf(socketMessage.getChatId())).writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(socketMessage)));
            } else {
                //处理私聊的任务，如果对方也在线,则推送消息
                ChannelId channelId = userMap.get(Long.valueOf(socketMessage.getChatId()));
                System.out.println("用户"+userMap);
                if (channelId != null) {
                    //对方在线则存入，redis中为在线消息类别
                    socketMessage.setStatus(true);
                    Channel ct = channelGroup.find(channelId);
                    //redis存储消息
                    messageStorage(socketMessage,keyHead+socketMessage.getChatId());
                    messageStorage(socketMessage,keyHead+socketMessage.getUserId());
                    if (ct != null) {
                        ct.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(socketMessage)));
                    }
                }else{
                    //如果对方不在线，则存入redis中
                    socketMessage.setStatus(false);
                    //redis存储消息
                    messageStorage(socketMessage,keyHead+socketMessage.getChatId());
                    messageStorage(socketMessage,keyHead+socketMessage.getUserId());

                }
            }
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }

    private static Long getUrlParams(String url) {
        if (!url.contains("=")) {
            return null;
        }
        String userId = url.substring(url.indexOf("=") + 1);
        return Long.parseLong(userId);
    }

    /*
    * 向redis 异步存储消息
    * */
    @Async
    public void messageStorage(SocketMessage socketMessage,String messageRediskey){
        redisOperatingUtil.listRightPush(messageRediskey, socketMessage);
        redisOperatingUtil.setKeyTime(messageRediskey,21600);
    }

}
