package com.hteos.demo.messager.websocket;

import java.util.Set;

import com.hteos.biz.model.UserVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.socket.*;

import com.hteos.demo.messager.MessageWebSocketManager;
import com.hteos.framework.bean.Position;

public class MessageWebSocketHandler implements WebSocketHandler {

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        UserVo account = this.getAccount(session);
        if (account == null) {
            return;
        }
        // 触发关闭事件，在连接池中移除连接
        MessageWebSocketManager.remove(account.getId());
        Set<String> onlineUser = MessageWebSocketManager.getOnlineUser();
        for (String user : onlineUser) {
            JSONObject result = new JSONObject();
            result.element("type", "leave");
            result.element("user", account.getId());
            MessageWebSocketManager.sendMessage(user, result.toString());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        UserVo account = this.getAccount(session);

        if(account == null){
            session.close();
            throw new IllegalStateException("access unauthorized");
        }

        MessageWebSocketManager.remove(account.getId());
        // 触发连接事件，在连接池中添加连接
        Set<String> onlineUser = MessageWebSocketManager.getOnlineUser();
        JSONObject onlines = new JSONObject();
        JSONArray array = new JSONArray();
        for (String user : onlineUser) {
            JSONObject json = new JSONObject();
            WebSocketSession session2 = MessageWebSocketManager.get(user);
            UserVo acc = this.getAccount(session2);
            json.element("id", acc.getId());
            json.element("name", acc.getNickName());
            Position pos = (Position) session2.getAttributes().get("position");
            if (pos != null) {
                if (pos.getCity() != null) {
                    json.element("group", pos.getProvince() + "-" + pos.getCity());
                } else {
                    json.element("group", pos.getProvince());
                }
            } else {
                json.element("group", "未知区域");
            }
            array.add(json);
        }
        onlines.element("type", "online");
        onlines.element("onlines", array);

        onlineUser = MessageWebSocketManager.getOnlineUser();
        for (String user : onlineUser) {
            // 通知所有用户上线
            JSONObject result = new JSONObject();
            JSONObject acc = new JSONObject();
            acc.element("id", account.getId());
            acc.element("name", account.getNickName());
            Position pos = (Position) session.getAttributes().get("position");
            if (pos != null) {
                acc.element("group", pos.getProvince() + "-" + pos.getCity());
            } else {
                acc.element("group", "未知区域");
            }
            result.element("type", "join");
            result.element("user", acc);
            MessageWebSocketManager.sendMessage(user, result.toString());
        }
        MessageWebSocketManager.put(account.getId(), session);
        if (array.size() > 0) {
            MessageWebSocketManager.sendMessage(account.getId(),
                    onlines.toString());
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        UserVo account = this.getAccount(session);
        try {
            JSONObject dialog = JSONObject.fromObject(String.valueOf(message.getPayload()));
            String type = dialog.getString("type");
            if (type.equals("message")) {
                String to = dialog.getString("to");
                MessageWebSocketManager.sendMessage(to, dialog.toString());
            } else if (type.equals("discuss")) {
                Set<String> onlineUser = MessageWebSocketManager.getOnlineUser();
                dialog.element("from", account.getNickName());
                for (String user : onlineUser) {
                    if (MessageWebSocketManager.get(user) == this) {
                        dialog.element("self", true);
                    }
                    MessageWebSocketManager.sendMessage(user, dialog.toString());
                }
            }
        } catch (Exception e) {

        }
    }

    private UserVo getAccount(WebSocketSession session) {
        UserVo userVo = (UserVo) session.getAttributes().get("hteos.userVo");
        return userVo;
    }

    @Override
    public void handleTransportError(WebSocketSession arg0, Throwable arg1)
            throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
