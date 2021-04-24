package dev.kolja.proximitychat.common;

import dev.kolja.proximitychat.common.netmessage.PingMessage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class CommonEventsHandler {
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
            PacketHandler.INSTANCE.sendToServer(new PingMessage());
            event.setCanceled(true);
            PacketHandler.setCurrentMessage("^" + Minecraft.getInstance().player.getScoreboardName() + "^ " + m.group("message"));
        }
    }
}
