package dev.kolja.proximitychat.net;

import dev.kolja.proximitychat.net.client.ChatClientHandler;
import dev.kolja.proximitychat.net.server.ChatServer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class OnPlayerLeaveHandler {
    @SubscribeEvent
    public static void onPlayerLeave(EntityLeaveWorldEvent event) {
        if(!(event.getEntity() instanceof PlayerEntity)) {
            return;
        }
        if(event.getWorld().isClientSide()) {
            assert Minecraft.getInstance().player != null;
            if(Minecraft.getInstance().player.equals(event.getEntity())) {
                ChatServer.terminate();
                ChatClientHandler.destroy();
            }
        }
    }
}
