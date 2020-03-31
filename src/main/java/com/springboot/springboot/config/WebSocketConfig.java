package com.springboot.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 注入websocket 配置
 */
@Configuration
@Slf4j
public class WebSocketConfig implements ApplicationContextAware {
    public static ApplicationContext appContext;
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        log.info("websocket启动成功");
        return new ServerEndpointExporter();

    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO Auto-generated method stub
        appContext = applicationContext;
    }
}
