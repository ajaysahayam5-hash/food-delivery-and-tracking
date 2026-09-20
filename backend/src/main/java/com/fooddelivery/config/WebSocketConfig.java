package com.fooddelivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @org.springframework.beans.factory.annotation.Value("${cors.allowed-origins:http://localhost:3000}")
    private String allowedOrigins;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
    /**
     * Registers STOMP endpoint with strict CORS from env.
     * Why: Review-II security — never leave WS open with * in production.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        String[] origins = java.util.Arrays.stream(allowedOrigins.split(","))
                .map(String::trim).filter(s -> !s.isEmpty()).toArray(String[]::new);
        registry.addEndpoint("/ws").setAllowedOriginPatterns(origins.length == 0 ? new String[]{"http://localhost:3000"} : origins).withSockJS();
    }
}
