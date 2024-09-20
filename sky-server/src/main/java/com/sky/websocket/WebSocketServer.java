package com.sky.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {
    private static Map<String, Session> sessionMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("客户端：" + sid + " 建立连接");
        sessionMap.put(sid, session);
    }

    @OnMessage
    public void onMessage(String message,@PathParam("sid") String sid) {
        System.out.println("客户端：" + sid + " 发送消息：" + message);

    }

    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("客户端：" + sid + " 关闭连接");
        sessionMap.remove(sid);
    }

    public void sentToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for(Session session: sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
