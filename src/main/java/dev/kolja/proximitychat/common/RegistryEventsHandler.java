package dev.kolja.proximitychat.common;

import dev.kolja.proximitychat.ProximityChatMod;
import dev.kolja.proximitychat.client.ProximityChatClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sun.rmi.runtime.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class RegistryEventsHandler {
    @SubscribeEvent
    public static void onRegisterEvent(RegisterCommandsEvent event) {
        new ProximityChatCommand(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onChatMessage(ClientChatEvent event) {
        String pattern = "^<\\s*(?<message>.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(event.getMessage());
        if(m.find()) {
            assert Minecraft.getInstance().player != null;
            event.setCanceled(true);
            ProximityChatClientHandler.getInstance().writeMsgToAll("^" + Minecraft.getInstance().player.getScoreboardName() + "^ " + m.group("message"));
        }
    }
}
