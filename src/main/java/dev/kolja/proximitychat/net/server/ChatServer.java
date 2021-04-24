package dev.kolja.proximitychat.net.server;

import dev.kolja.proximitychat.ProximityChatMod;
import dev.kolja.proximitychat.common.netmessage.ConnectionBuildMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatServer extends Thread {

    private static ChatServer server;

    public static void create(ConnectionBuildMessage list) {
        if(server != null) {
            return;
        }
        server = new ChatServer(list.read().get(0));
        server.start();
    }

    public static void terminate() {
        server.kill();
        server = null;
    }

    private boolean shouldStop = false;
    private String ip;
    private List<ChatServerConn> socketList;

    private ChatServer(String ip) {
        super();
        this.ip = ip;
    }

    public void run() {
        ServerSocket server;
        socketList = new LinkedList<>();

        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), ProximityChatMod.SOCKET_PORT));
            ProximityChatMod.LOGGER.info("Server socket created");
        } catch (IOException e) {
            ProximityChatMod.LOGGER.error("Could not create server socket");
            return;
        }
        while(!shouldStop) {
            try {
                Socket socket = server.accept();
                ProximityChatMod.LOGGER.info("Incoming connection accepted");
                ChatServerConn conn = new ChatServerConn(socket);
                conn.start();
                socketList.add(conn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void kill() {
        shouldStop = true;
        for(ChatServerConn conn : socketList) {
            conn.terminate();
        }
    }
}
