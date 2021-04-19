package dev.kolja.proximitychat.common;

import dev.kolja.proximitychat.ProximityChatMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.PacketDistributor;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@EventBusSubscriber(Dist.DEDICATED_SERVER)
public class NetworkEventHandler {
    public static void onMessagePing(PingMessage ping) {

    }

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        if(!(event.getEntity() instanceof PlayerEntity)) {
            return;
        }
        if(event.getEntity() instanceof ServerPlayerEntity) {
            ProximityChatMod.LOGGER.info("Player joined");
        }
        ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();
        if(!event.getWorld().isClientSide) {
            List<String> ipList = new LinkedList<>();
            List<ServerPlayerEntity> list = player.getServer().getPlayerList().getPlayers();
            addToList(player, ipList);
            for(ServerPlayerEntity entity : list) {
                if(entity.equals(player))
                    continue;
                addToList(entity, ipList);
            }
            for(ServerPlayerEntity entity : list) {
                ProximityChatPacketHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> entity),
                        new ConnectionBuildMessage(
                                new LinkedList<>(Collections.singletonList(getIpFromEntity(player)))
                        ));
            }
            ProximityChatPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new ConnectionBuildMessage(ipList));
            ProximityChatMod.LOGGER.info("Client List sent");
        }
    }

    private static void addToList(ServerPlayerEntity entity, List<String> list) {
        list.add(getIpFromEntity(entity));
    }

    public static String getIpFromEntity(ServerPlayerEntity entity) {
        SocketAddress address = entity.connection.getConnection().getRemoteAddress();
        String s = address.toString().substring(1);
        String[] s1 = s.split(":");
        ProximityChatMod.LOGGER.info("IP: " + s1[0]);
        return s1[0];
    }
}
