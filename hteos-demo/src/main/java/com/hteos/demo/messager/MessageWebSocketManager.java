package com.hteos.demo.messager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class MessageWebSocketManager {

	// FIXME 是否要实时更新在线用户和用户组

	private static Map<String, WebSocketSession> connections = new HashMap<String, WebSocketSession>();

	public static void put(String user, WebSocketSession session) {
		// 添加连接
		connections.put(user, session);
	}

	public static Set<String> getOnlineUser() {
		return connections.keySet();
	}
	
	public static WebSocketSession get(String id){
		return connections.get(id);
	}

	public static boolean isOnline(String id) {
		return (connections.get(id) != null);
	}

	public static void remove(String user) {
		// 移除连接
		connections.remove(user);
	}

	public static boolean sendMessage(String user, String message) {
		// 向特定的用户发送数据
		try {
			WebSocketSession session = connections.get(user);
			// 连接了WebSocket，使用WebSocket发送消息

			if (session != null) {
				synchronized (session) {
					session.sendMessage(new TextMessage(message));
					return true;
				}
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
