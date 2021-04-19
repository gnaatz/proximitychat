package dev.kolja.proximitychat.client;

import dev.kolja.proximitychat.ProximityChatMod;
import dev.kolja.proximitychat.common.ClientList;

import java.util.HashMap;

public class ProximityChatClientHandler {

    private static ProximityChatClientHandler handler;

    public static ProximityChatClientHandler getInstance() {
        if(handler == null) {
            handler = new ProximityChatClientHandler();
        }
        return handler;
    }

    public static void destroy() {
        if(handler == null) {
            return;
        }
        for(ProximityChatClientConn conn : handler.map.values()) {
            conn.terminate();
        }
        handler = null;
    }

    private final HashMap<String, ProximityChatClientConn> map;

    private  ProximityChatClientHandler() {
        map = new HashMap<>();
    }

    public void createConns(ClientList list) {
        for(String ip : list.read()) {
            if(map.containsKey(ip))
                continue;
            map.put(ip, new ProximityChatClientConn(ip, ProximityChatMod.SOCKET_PORT));
        }
    }

    public void terminateConn(String client) {
        map.get(client).terminate();
        map.remove(client);
    }

    public void writeMsgToConns(ClientList list, String msg) {
        for(String client : list.read()) {
            map.get(client).writeMessage(msg);
        }
    }

    public void writeMsgToAll(String msg) {
        for(ProximityChatClientConn conn : map.values()) {
            conn.writeMessage(msg);
        }
    }
}
