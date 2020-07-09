package com.springboot.springboot.tasks;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class SocketIoEventHandler {
    /**
     * 会话集合
     */
//    private static final ConcurrentSkipListMap<String, ClientInfo> webSocketMap = new ConcurrentSkipListMap<>();
//    private static final ConcurrentSkipListMap<String, SocketIOClient> webSocketMap = new ConcurrentSkipListMap<>();

    /**
     * 记录静态连接数（原子类线程安全）
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    @Autowired

    private SocketIOServer server;

    private SocketIOClient client;

    /**
     * connect 客户端发起调用
     */

    @OnConnect
    public void onConnect(SocketIOClient client) {
        this.client = client;
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        //join room
        client.joinRoom("room007");
        log.info("客户端："+client.getSessionId() +" 已连接: "+clientId);
        aa(client);
    }

    public void aa(SocketIOClient client) {
        BroadcastOperations name = client.getNamespace().getRoomOperations("room007");
        Collection<SocketIOClient> clients = name.getClients();
        for (SocketIOClient s: clients) {
            log.info(s.getHandshakeData().getSingleUrlParam("clientid"));
        }
    }


    /**
     * 客户端断开调用
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        client.leaveRoom("room007");
        log.info("客户端:" + client.getSessionId() + "断开连接");
    }


    @OnEvent(value = "message")
    public void onEvent(SocketIOClient client, AckRequest request, String data){
        log.info("发来消息：" + data);
        //回发消息
//        client.sendEvent("messageevent", "我是服务器都安发送的信息");
    }
}
