package dev.kolja.proximitychat.net.client;

import dev.kolja.proximitychat.ProximityChatMod;
import dev.kolja.proximitychat.common.netmessage.ConnectionBuildMessage;
import dev.kolja.proximitychat.common.netmessage.ReceiverMessage;

import java.util.HashMap;

public class ChatClientHandler {

    private static ChatClientHandler handler;

    public static ChatClientHandler getInstance() {
        if(handler == null) {
            handler = new ChatClientHandler();
        }
        return handler;
    }

    public static void destroy() {
        if(handler == null) {
            return;
        }
        for(ChatClientConn conn : handler.map.values()) {
            conn.terminate();
        }
        handler = null;
    }

    private final HashMap<String, ChatClientConn> map;

    private ChatClientHandler() {
        map = new HashMap<>();
    }

    public void createConns(ConnectionBuildMessage list) {
        for(String ip : list.read()) {
            if(map.containsKey(ip))
                continue;
            map.put(ip, new ChatClientConn(ip, ProximityChatMod.SOCKET_PORT));
        }
    }

    public void terminateConn(String client) {
        map.get(client).terminate();
        map.remove(client);
    }

    public void writeMsgToConns(ReceiverMessage list, String msg) {
        for(String client : list.read()) {
            map.get(client).writeMessage(msg);
        }
    }

    public void writeMsgToAll(String msg) {
        for(ChatClientConn conn : map.values()) {
            conn.writeMessage(msg);
        }
    }
}
