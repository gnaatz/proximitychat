package dev.kolja.proximitychat.common;

import dev.kolja.proximitychat.ProximityChatMod;
import dev.kolja.proximitychat.client.ProximityChatClientHandler;
import dev.kolja.proximitychat.client.ProximityChatServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ProximityChatPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ProximityChatMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void handleConnectedClientsList(ClientList msg, Supplier<NetworkEvent.Context> ctx) {
        ProximityChatMod.LOGGER.info("Client List received");
        ProximityChatServer.create();
        ProximityChatClientHandler.createConns(msg);
    }
}
