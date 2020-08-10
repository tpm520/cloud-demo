package com.tpblog.websocket.controller;

import com.tpblog.websocket.util.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsocketController {

    @Autowired
    WebSocket webSocket;

    @GetMapping("/sendTo")
    public String sendTo(@RequestParam("msg") String msg,@RequestParam("userId") String userId){

        webSocket.sendMessageTo(msg, userId);
        return "发送成功";
    }

    @GetMapping("/sendAll")
    public String sendAll(String msg){
        webSocket.sendMessageAll(msg,"system");
        return "发送成功";
    }

    @MessageMapping("/connection/{user}")
    public String connUser(String user){

        return "连接成功";
    }
}
