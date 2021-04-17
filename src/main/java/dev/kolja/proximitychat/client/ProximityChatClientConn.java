package dev.kolja.proximitychat.client;

import dev.kolja.proximitychat.ProximityChatMod;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ProximityChatClientConn extends Thread {
    private Socket socket;
    private String msg;
    private PrintWriter os;

    public ProximityChatClientConn(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            os = new PrintWriter(socket.getOutputStream());
            ProximityChatMod.LOGGER.info("Socket and OS for " + ip + " created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        os.println(msg);
    }

    public void writeMessage(String msg) {
        this.msg = msg;
        this.start();
    }

    public void terminate() {
        try {
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
