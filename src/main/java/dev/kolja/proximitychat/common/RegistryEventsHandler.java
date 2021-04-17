package dev.kolja.proximitychat.common;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RegistryEventsHandler {
    @SubscribeEvent
    public static void onRegisterEvent(RegisterCommandsEvent event) {
        new ProximityChatCommand(event.getDispatcher());
    }
}
