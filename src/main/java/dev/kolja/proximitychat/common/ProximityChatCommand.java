package dev.kolja.proximitychat.common;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ProximityChatCommand {

    public ProximityChatCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("chat")
                .then(
                        Commands.argument("message", StringArgumentType.greedyString())
                )
        );
    }
}
