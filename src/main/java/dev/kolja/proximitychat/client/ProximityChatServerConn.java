package dev.kolja.proximitychat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProximityChatServerConn extends Thread {

    private Socket socket;
    private boolean shouldQuit = false;

    public ProximityChatServerConn(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        BufferedReader is;
        try {
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(!shouldQuit) {
            try {
                String line = is.readLine();
                //TODO: pass line to handler function
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
