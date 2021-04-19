package dev.kolja.proximitychat.client;

import dev.kolja.proximitychat.ProximityChatMod;

import java.io.*;
import java.net.Socket;

public class ProximityChatServerConn extends Thread {

    private Socket socket;
    private boolean shouldQuit = false;

    public ProximityChatServerConn(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        DataInputStream is;
        try {
            is = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(!shouldQuit) {
            try {
                ProximityChatMod.LOGGER.error("Waiting for message");
                String line = is.readUTF();
                ProximityChatMod.LOGGER.error("received message: " + line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        shouldQuit = true;
    }
}
