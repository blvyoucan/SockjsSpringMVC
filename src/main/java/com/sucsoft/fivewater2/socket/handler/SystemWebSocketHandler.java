package com.sucsoft.fivewater2.socket.handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.sucsoft.fivewater2.common.CommonConstants;

public class SystemWebSocketHandler implements WebSocketHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
	
	private static final Map<String,WebSocketSession> user_session = new ConcurrentSkipListMap<String,WebSocketSession>();
	

	@Override
	public void afterConnectionClosed(WebSocketSession arg0, CloseStatus arg1) throws Exception {
		String userkey = (String)arg0.getAttributes().get(CommonConstants.CURRENT_USER_KEY);
		if( userkey != null && user_session.containsKey(userkey) ){
			user_session.remove(userkey);
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession arg0) throws Exception {
		String userkey = (String)arg0.getAttributes().get(CommonConstants.CURRENT_USER_KEY);
		if( userkey != null ){
			user_session.put( userkey, arg0 );
		}
	}

	@Override
	public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1) throws Exception {
		
	}

	@Override
	public void handleTransportError(WebSocketSession arg0, Throwable arg1) throws Exception {
		if( arg0.isOpen() ){
			arg0.close();
		}
		String userkey = (String)arg0.getAttributes().get(CommonConstants.CURRENT_USER_KEY);
		user_session.remove(userkey);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	public static void sendToUsers(String message){
		user_session.forEach( (userkey,user) -> {
			if( user != null){
				if( user.isOpen() ){
					try {
						user.sendMessage(new TextMessage(message) );
					} catch (IOException e) {
						if( logger.isErrorEnabled() ){
							logger.error("发送socket消息出错{},{}",userkey ,e.getMessage() );
						}
					}
				}
			}
		});
	}
	
	public static void sendToUser(String userkey,String message){
		WebSocketSession user = user_session.get(userkey);
		if( user != null){
			if( user.isOpen() ){
				try {
					user.sendMessage(new TextMessage(message) );
				} catch (IOException e) {
					if( logger.isErrorEnabled() ){
						logger.error("发送socket消息出错{},{}",userkey ,e.getMessage() );
					}
				}
			}
		}
	}

}
