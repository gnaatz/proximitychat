package dev.kolja.proximitychat.client;

import dev.kolja.proximitychat.ProximityChatMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

import java.io.*;
import java.net.Socket;

public class ProximityChatServerConn extends Thread {

    private final Socket socket;
    private boolean shouldQuit = false;

    public ProximityChatServerConn(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        DataInputStream is;
        try {
            is = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            ProximityChatMod.LOGGER.error("Could not create Input stream for server socket");
            return;
        }

        while(!shouldQuit) {
            try {
                String line = is.readUTF();
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.sendMessage(new StringTextComponent(line), Minecraft.getInstance().player.getUUID());
            } catch (IOException e) {
                ProximityChatMod.LOGGER.error("IOException while receiving message");
                shouldQuit = true;
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            ProximityChatMod.LOGGER.error("Could not close server conn");
        }
    }

    public void terminate() {
        shouldQuit = true;
        try {
            this.join();
        } catch (InterruptedException e) {
            ProximityChatMod.LOGGER.debug("Something went wrong. Disregarding");
        }
    }
}
