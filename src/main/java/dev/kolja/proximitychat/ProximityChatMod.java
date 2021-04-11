package dev.kolja.proximitychat;

import dev.kolja.proximitychat.common.ClientList;
import dev.kolja.proximitychat.common.ProximityChatPacketHandler;
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
        ProximityChatPacketHandler.INSTANCE.registerMessage(index++, ClientList.class, ClientList::encode, ClientList::decode, ProximityChatPacketHandler::handleConnectedClientsList);
    }
}
