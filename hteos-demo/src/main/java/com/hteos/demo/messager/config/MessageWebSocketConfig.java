package com.hteos.demo.messager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.hteos.demo.messager.websocket.MessageWebSocketHandler;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

@Configuration
@EnableWebSocket
public class MessageWebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageWebSocketHandler(), "/ws/messager")
                .addInterceptors(handshakeInterceptor()).setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler messageWebSocketHandler() {
        return new MessageWebSocketHandler();
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new HttpSessionHandshakeInterceptor();
    }

}
