package com.tpblog.websocket.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.util.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@ServerEndpoint(value = "/connection/{user}")
public class WebSocketUtil {
    // 连接人数
    public static int onlineNum;
    // 连接用户
    public static String user;
    // 连接会话
    private Session session;
    // 保存连接用户
    private HashMap<String, WebSocketUtil> clients = new HashMap<String, WebSocketUtil>();

    /**
     * 发送消息都指定用户
     * @param user
     * @param msg
     */
    public void sendMessageTo(String user, String msg){
        for (WebSocketUtil client : clients.values()) {
            if (client.user.equals(user)) {
                client.session.getAsyncRemote().sendText(msg);
            }
        }
    }

    /**
     * 发送消息给指定用户
     * @param msg
     */
    public void sendMessageAll(String msg) {
        for (WebSocketUtil client : clients.values()) {
            client.session.getAsyncRemote().sendText(msg);
        }
    }

    /**
     * 创建连接
     * @param user
     */
    @OnOpen
    private void onLine(@PathParam("user") String user, Session session){
        onlineNum++;
        this.user = user;
        this.session = session;
        // 保存上线用户
        clients.put(user, this);
        log.info("有人上线啦...");
        // 推送连接情况
        sendOnlineNumAll();
    }

    /**
     * 断开连接
     * @param user
     */
    @OnClose
    private void onClose(String user) {
        onlineNum--;
        clients.remove(user);
        log.info("有人下线啦...");
        Map<String, Object> conn = Maps.newHashMap();
        // 推送连接情况
        sendOnlineNumAll();
    }

    /**
     * 接收用户信息并发送给指定用户
     * @param msg
     */
    @OnMessage
    private void onMessage(String msg) {
        JSONObject jsonObject = JSON.parseObject(msg);

        String messageText = jsonObject.getString("data");
        String fromUser = jsonObject.getString("from");
        String toUser = jsonObject.getString("to");

        Map<String, Object> mp = Maps.newHashMap();
        mp.put("messageText", messageText);
        mp.put("fromUser", fromUser);
        if (toUser.equals("all")) {
            sendMessageAll(JSON.toJSONString(mp));
        }else {
            sendMessageTo(toUser, JSON.toJSONString(mp));
        }
    }

    /**
     * 向所有人推送在线情况
     */
    private void sendOnlineNumAll(){
        Map<String, Object> conn = Maps.newHashMap();
        conn.put("在线人数", clients.size());
        conn.put("在线人员", clients.keySet());
        sendMessageAll(JSON.toJSONString(conn));
    }
}
