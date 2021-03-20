package com.vincent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置类
 */
@EnableAsync
@Configuration
@EnableWebSocketMessageBroker   //表示开启使用STOMP协议来传输基于代理的消息
public class WsConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 注册 Stomp的端点
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")  //注册一个端点，websocket的访问地址
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user/", "/topic/");   //推送消息前缀
        registry.setApplicationDestinationPrefixes("/app");
    }
}
