package dev.kolja.proximitychat.client;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientNetworkEventHandler {
    @SubscribeEvent
    public static void onPlayerLeave(EntityLeaveWorldEvent event) {
        if(!(event.getEntity() instanceof PlayerEntity)) {
            return;
        }
        if(event.getWorld().isClientSide()) {
            ProximityChatServer.terminate();
            ProximityChatClientHandler.terminateConns();
        }
    }
}
