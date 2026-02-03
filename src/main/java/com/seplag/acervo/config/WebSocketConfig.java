package com.seplag.acervo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // clientes assinam /topic/...
        registry.enableSimpleBroker("/topic");
        // mensagens do cliente para o servidor (se você usar @MessageMapping) vão para /app/...
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint de handshake websocket (ex.: ws://host/ws)
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
        // se usar SockJS no front, habilite:
        // registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}
