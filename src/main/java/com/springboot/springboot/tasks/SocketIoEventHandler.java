package com.springboot.springboot.tasks;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.springboot.springboot.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class SocketIoEventHandler {
    /**
     * 会话集合
     */
//    private static final ConcurrentSkipListMap<String, ClientInfo> webSocketMap = new ConcurrentSkipListMap<>();
    private static final ConcurrentSkipListMap<String, SocketIOClient> webSocketMap = new ConcurrentSkipListMap<>();

    /**
     * 记录静态连接数（原子类线程安全）
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    @Autowired

    private SocketIOServer server;


    /**
     * connect 客户端发起调用
     */

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        webSocketMap.put(clientId,client);
        log.info("客户端："+client.getSessionId() +"已连接: "+clientId);
    }


    /**
     * 客户端断开调用
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        log.info("客户端:" + client.getSessionId() + "断开连接");
    }


    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, Message data){
        log.info("发来消息：" + data);
        //回发消息
        client.sendEvent("messageevent", "我是服务器都安发送的信息");
    }
}
