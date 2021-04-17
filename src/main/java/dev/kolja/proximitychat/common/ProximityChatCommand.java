package dev.kolja.proximitychat.common;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.kolja.proximitychat.ProximityChatMod;
import dev.kolja.proximitychat.client.ProximityChatClientHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ProximityChatCommand {

    public ProximityChatCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("chat").then(
                Commands.argument("message", StringArgumentType.greedyString())
                        .executes(context -> {
                            ProximityChatClientHandler.getInstance().writeMsgToAll(context.getArgument("message", String.class));
                            return 0;
                        })
                ).executes(context -> {
                    ProximityChatMod.LOGGER.debug("Chat command used without message");
                    return 0;
                })
        );
    }
}
