package dev.kolja.proximitychat.client;

import dev.kolja.proximitychat.ProximityChatMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

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
                String line = is.readUTF();
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.sendMessage(new StringTextComponent(line), Minecraft.getInstance().player.getUUID());
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
