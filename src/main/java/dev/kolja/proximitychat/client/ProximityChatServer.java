package dev.kolja.proximitychat.client;

import dev.kolja.proximitychat.ProximityChatMod;
import dev.kolja.proximitychat.common.ClientList;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ProximityChatServer extends Thread {

    private static ProximityChatServer server;

    public static void create(ClientList list) {
        if(server != null) {
            return;
        }
        server = new ProximityChatServer(list.read().get(0));
        server.start();
    }

    public static void terminate() {
        server.kill();
        server = null;
    }

    private boolean shouldStop = false;
    private String ip;
    private List<ProximityChatServerConn> socketList;

    private ProximityChatServer(String ip) {
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
            e.printStackTrace();
            return;
        }
        while(!shouldStop) {
            try {
                Socket socket = server.accept();
                ProximityChatMod.LOGGER.info("Incoming connection accepted");
                ProximityChatServerConn conn = new ProximityChatServerConn(socket);
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
        for(ProximityChatServerConn conn : socketList) {
            conn.terminate();
        }
    }
}
