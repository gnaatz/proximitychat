package dev.kolja.proximitychat.client;

import dev.kolja.proximitychat.ProximityChatMod;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ProximityChatServer extends Thread {

    private static ProximityChatServer server;

    public static void create() {
        if(server != null) {
            return;
        }
        server = new ProximityChatServer();
        server.start();
    }

    public static void terminate() {
        server.kill();
        server = null;
    }

    private boolean shouldStop = false;
    private List<ProximityChatServerConn> socketList;

    public void run() {
        ServerSocket server;
        socketList = new LinkedList<>();

        try {
            server = new ServerSocket(ProximityChatMod.SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while(!shouldStop) {
            try {
                Socket socket = server.accept();
                socketList.add(new ProximityChatServerConn(socket));
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
