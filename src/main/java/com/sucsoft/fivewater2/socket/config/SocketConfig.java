package com.sucsoft.fivewater2.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.sucsoft.fivewater2.socket.handler.SystemWebSocketHandler;
import com.sucsoft.fivewater2.socket.interceptor.WebSocketHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry regs) {
		regs.addHandler(systemWebSocketHandler(), "socketService")
			.addInterceptors(handshakeInterceptor());
		regs.addHandler(systemWebSocketHandler(), "sockjs/socketService")
			.addInterceptors(handshakeInterceptor()).withSockJS();
	}

	@Bean
    public WebSocketHandler systemWebSocketHandler(){
        return new SystemWebSocketHandler();
    }
	
	@Bean
	public HandshakeInterceptor handshakeInterceptor(){
		return new WebSocketHandshakeInterceptor();
	}
}
