package dev.kolja.proximitychat.common;

import net.minecraft.network.PacketBuffer;

public class PingMessage {

    public void encode(PacketBuffer packetBuffer) {
    }

    public static PingMessage decode(PacketBuffer packetBuffer) {
        return new PingMessage();
    }
}
