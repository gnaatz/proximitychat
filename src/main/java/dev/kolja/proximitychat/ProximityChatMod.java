package dev.kolja.proximitychat;

import com.mojang.brigadier.CommandDispatcher;
import dev.kolja.proximitychat.common.ConnectionBuildMessage;
import dev.kolja.proximitychat.common.PingMessage;
import dev.kolja.proximitychat.common.ProximityChatPacketHandler;
import dev.kolja.proximitychat.common.ReceiverMessage;
import net.minecraft.command.CommandSource;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(ProximityChatMod.MODID)
public class ProximityChatMod {

    public static final String MODID = "proximitychat";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final int SOCKET_PORT = 2020;

    public ProximityChatMod() {
        LOGGER.debug(MODID + " loaded");

        int index = 0;
        ProximityChatPacketHandler.INSTANCE.registerMessage(index++, ConnectionBuildMessage.class, ConnectionBuildMessage::encode, ConnectionBuildMessage::decode, ProximityChatPacketHandler::handleConnectedClientsList);
        ProximityChatPacketHandler.INSTANCE.registerMessage(index++, PingMessage.class, PingMessage::encode, PingMessage::decode, ProximityChatPacketHandler::handleMessagePing);
        ProximityChatPacketHandler.INSTANCE.registerMessage(index++, ReceiverMessage.class, ReceiverMessage::encode, ReceiverMessage::decode, ProximityChatPacketHandler::handleReceiverMessage);
        CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

    }
}
