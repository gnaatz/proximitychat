package dev.kolja.proximitychat.common;

import dev.kolja.proximitychat.ProximityChatMod;
import dev.kolja.proximitychat.client.ProximityChatClientHandler;
import dev.kolja.proximitychat.client.ProximityChatServer;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class ProximityChatPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ProximityChatMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static String currentMessage;

    public static void handleConnectedClientsList(ConnectionBuildMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ProximityChatMod.LOGGER.info("Client List received");
        ProximityChatServer.create(msg);
        ProximityChatClientHandler.getInstance().createConns(msg);
    }

    public static void handleMessagePing(PingMessage msg, Supplier<NetworkEvent.Context> ctx) {
        List<PlayerEntity> playerEntityList = ctx.get().getSender().getCommandSenderWorld().getNearbyPlayers(EntityPredicate.DEFAULT, ctx.get().getSender(), ctx.get().getSender().getBoundingBox().inflate(100));
        playerEntityList.add(ctx.get().getSender());
        List<String> ips = new LinkedList<>();
        for(PlayerEntity entity : playerEntityList) {
            ips.add(NetworkEventHandler.getIpFromEntity((ServerPlayerEntity) entity));
        }
        ProximityChatPacketHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> ctx.get().getSender()),
                new ReceiverMessage(
                    ips
                ));
    }

    public static void setCurrentMessage(String msg) {
        currentMessage = msg;
    }

    public static void handleReceiverMessage(ReceiverMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ProximityChatClientHandler.getInstance().writeMsgToConns(msg, currentMessage);
    }
}
