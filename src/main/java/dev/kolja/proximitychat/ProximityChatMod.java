package dev.kolja.proximitychat;

import dev.kolja.proximitychat.common.PacketHandler;
import dev.kolja.proximitychat.common.netmessage.ConnectionBuildMessage;
import dev.kolja.proximitychat.common.netmessage.PingMessage;
import dev.kolja.proximitychat.common.netmessage.ReceiverMessage;
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
        PacketHandler.INSTANCE.registerMessage(index++, ConnectionBuildMessage.class, ConnectionBuildMessage::encode, ConnectionBuildMessage::decode, PacketHandler::handleConnectedClientsList);
        PacketHandler.INSTANCE.registerMessage(index++, PingMessage.class, PingMessage::encode, PingMessage::decode, PacketHandler::handleMessagePing);
        PacketHandler.INSTANCE.registerMessage(index++, ReceiverMessage.class, ReceiverMessage::encode, ReceiverMessage::decode, PacketHandler::handleReceiverMessage);
    }
}
