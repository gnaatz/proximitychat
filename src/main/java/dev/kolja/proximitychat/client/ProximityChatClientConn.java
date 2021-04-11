package dev.kolja.proximitychat.client;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ProximityChatClientConn extends Thread {
    private Socket socket;
    private String msg;

    public ProximityChatClientConn(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            os.println(msg);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessage(String msg) {
        this.msg = msg;
        this.start();
    }

    public void terminate() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
