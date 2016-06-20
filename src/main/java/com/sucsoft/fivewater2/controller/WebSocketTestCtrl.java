package com.sucsoft.fivewater2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sucsoft.fivewater2.common.CommonConstants;
import com.sucsoft.fivewater2.socket.handler.SystemWebSocketHandler;

@Controller
@RequestMapping(value="/test")
public class WebSocketTestCtrl {
	
	
	@RequestMapping(value="/test")
	@ResponseBody
	public void test(HttpSession session){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					String userkey = (String)session.getAttribute(CommonConstants.CURRENT_USER_KEY);
					if( userkey != null ){
						SystemWebSocketHandler.sendToUser(userkey,"Hello World!");
					}
					try {
						Thread.currentThread().sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
}
