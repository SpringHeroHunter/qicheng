package com.springboot.springboot.utils;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * socket启动类
 */
@Component
@Slf4j
public class ServerRunner implements CommandLineRunner {
    private final SocketIOServer server;
    @Autowired
    public ServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
        log.info("socket.io启动成功！");
    }
}
