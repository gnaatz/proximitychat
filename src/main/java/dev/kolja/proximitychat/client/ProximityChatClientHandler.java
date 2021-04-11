package dev.kolja.proximitychat.client;

import dev.kolja.proximitychat.ProximityChatMod;
import dev.kolja.proximitychat.common.ClientList;

import java.util.HashMap;

public class ProximityChatClientHandler {

    private static ProximityChatClientHandler handler;

    public static void createConns(ClientList list) {
        if(handler != null) {
            return;
        }
        handler = new ProximityChatClientHandler(list);
    }

    public static ProximityChatClientHandler getInstance() throws HandlerNotCreatedException {
        if(handler == null) {
            throw new HandlerNotCreatedException();
        }
        return handler;
    }

    public static void terminateConns() {
        for(ProximityChatClientConn conn : handler.map.values()) {
            conn.terminate();
        }
        handler = null;
    }

    private HashMap<String, ProximityChatClientConn> map;

    private  ProximityChatClientHandler(ClientList clientList) {
        map = new HashMap<>();
        for(String ip : clientList.read()) {
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
}
