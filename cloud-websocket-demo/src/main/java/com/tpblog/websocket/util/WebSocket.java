package com.tpblog.websocket.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.util.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/connect/{userId}")
@Slf4j
public class WebSocket {

    // 在线人数
    public static int onlineNum = 0;

    // 保存所有用户连接信息，key为用户名
    public static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();

    private Session session;

    private String userId;

    /**
     * 建立连接
     * @param userId
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        onlineNum++;
        log.info("连接各户id:"+ session.getId()+", 用户名："+userId);
        this.userId = userId;
        this.session = session;

        //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
        Map<String,Object> map1 = Maps.newHashMap();
        map1.put("messageType",1);
        map1.put("userId",userId);
        // 给所有人发送上线通知
        sendMessageAll(JSON.toJSONString(map1), userId);

        // 保存用户
        clients.put(userId, this);
        log.info("有新连接，当前连接人数："+ clients.size());
        // 给自己发送在线人员信息
        Map<String, Object> map2 = Maps.newHashMap();
        map2.put("messageType", 3);

        // 移除自己
        Set<String> set = clients.keySet();
        map2.put("onlineUsers", set);
        sendMessageTo(JSON.toJSONString(map2), userId);

    }

    @OnError
    public void onError(Session session, Throwable throwable){
        log.error("服务端发生错误，错误信息："+throwable.getMessage());
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(){
        onlineNum--;

        clients.remove(userId);
        Map<String, Object> mp = Maps.newHashMap();
        mp.put("messageType",2);
        mp.put("onlineUsers",clients.keySet());
        mp.put("userId",userId);
        sendMessageAll(JSON.toJSONString(mp), userId);

        log.info("有人线下，当前在线人数："+clients.size());
    }

    /**
     * 接收客户端消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session){
        log.info("客户端消息："+message+", 客户端id: "+session.getId());

        // 获取消息中的信息
        JSONObject jsonObject = JSON.parseObject(message);
        String fromUser = jsonObject.getString("userId");
        String toUser = jsonObject.getString("to");
        String textMessage = jsonObject.getString("message");

        // 广播消息
        Map<String, Object> mp = Maps.newHashMap();
        mp.put("messageType", 4);
        mp.put("textMessage", textMessage);
        mp.put("fromUserId", fromUser);
        // 判断是给个人发送还是所有人
        if (toUser.equals("所有人")) {
            sendMessageAll(JSON.toJSONString(mp), fromUser);
        }else {
            mp.put("toUserId", toUser);
            log.info("开始向"+toUser+"发送消息");
            sendMessageTo(JSON.toJSONString(mp), toUser);
        }

    }
    /**
     * 给指定用户发送消息
     * @param message
     * @param userId
     */
    public void sendMessageTo(String message, String userId){
        for (WebSocket webSocket: clients.values()) {
            if (webSocket.userId.equals(userId)) {
                webSocket.session.getAsyncRemote().sendText(message);
                break;
            }
        }
    }
    /**
     * 给所有人发送消息
     * @param message
     * @param fromUserId
     */
    public void sendMessageAll(String message, String fromUserId){
        for (WebSocket webSocket : clients.values()) {
            webSocket.session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 获取连接人数
     * @return
     */
    public static synchronized int getOnlineNum(){
        return onlineNum;
    }
}
