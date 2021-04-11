package dev.kolja.proximitychat.common;

import dev.kolja.proximitychat.ProximityChatMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.PacketDistributor;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.List;

@EventBusSubscriber(Dist.DEDICATED_SERVER)
public class NetworkEventHandler {
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        if(!(event.getEntity() instanceof PlayerEntity)) {
            return;
        }
        if(event.getEntity() instanceof ServerPlayerEntity) {
            ProximityChatMod.LOGGER.error("is ServerPlayerEntity");
        }
        ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();
        if(!event.getWorld().isClientSide) {
            List<String> ipList = new LinkedList<>();
            List<ServerPlayerEntity> list = player.getServer().getPlayerList().getPlayers();
            for(ServerPlayerEntity entity : list) {
                if(entity.equals(player))
                    continue;
                SocketAddress address = entity.connection.getConnection().getRemoteAddress();
                ipList.add(((InetSocketAddress) address).getAddress().getHostName());
            }
            ProximityChatPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new ClientList(ipList));
        }
    }
}
