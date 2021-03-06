package dev.kolja.proximitychat.net.client;

import dev.kolja.proximitychat.ProximityChatMod;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientConn {
    private Socket socket;
    private String msg;
    private DataOutputStream os;
    private Thread currentThread;

    public ChatClientConn(String ip, int port) {
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
        if(currentThread != null) {
            try {
                currentThread.join();
            } catch (InterruptedException | NullPointerException e) {
                ProximityChatMod.LOGGER.debug("Something went wrong. Disregarding");
            }
        }
        currentThread = new Thread(this::run);
        currentThread.start();
    }

    public void terminate() {
        try {
            currentThread.join();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException | NullPointerException e) {
            ProximityChatMod.LOGGER.debug("Something went wrong. Disregarding");
        }
    }
}
