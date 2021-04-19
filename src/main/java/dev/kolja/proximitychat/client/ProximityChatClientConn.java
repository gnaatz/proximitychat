package dev.kolja.proximitychat.client;

import dev.kolja.proximitychat.ProximityChatMod;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ProximityChatClientConn extends Thread {
    private Socket socket;
    private String msg;
    private DataOutputStream os;

    public ProximityChatClientConn(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            os = new DataOutputStream(socket.getOutputStream());
            ProximityChatMod.LOGGER.info("Socket and OS for " + ip + " created");
        } catch (IOException e) {
            ProximityChatMod.LOGGER.error("Couldn't create socket");
        }
    }

    public void run() {
        try {
            os.writeUTF(msg);
            ProximityChatMod.LOGGER.error("Wrote: " + msg);
        } catch (IOException e) {
            ProximityChatMod.LOGGER.error("Couldn't send msg");
        }
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
