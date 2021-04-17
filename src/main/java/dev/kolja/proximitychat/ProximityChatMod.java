package dev.kolja.proximitychat;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.kolja.proximitychat.common.ClientList;
import dev.kolja.proximitychat.common.ProximityChatPacketHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

@Mod(ProximityChatMod.MODID)
public class ProximityChatMod {

    public static final String MODID = "proximitychat";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final int SOCKET_PORT = 2020;

    public ProximityChatMod() {
        LOGGER.debug(MODID + " loaded");

        int index = 0;
        ProximityChatPacketHandler.INSTANCE.registerMessage(index++, ClientList.class, ClientList::encode, ClientList::decode, ProximityChatPacketHandler::handleConnectedClientsList);
        CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

    }
}
