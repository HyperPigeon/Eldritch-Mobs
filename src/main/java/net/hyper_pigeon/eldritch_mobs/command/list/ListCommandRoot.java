package net.hyper_pigeon.eldritch_mobs.command.list;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static net.hyper_pigeon.eldritch_mobs.command.list.chunk.ListChunkCommandRoot.LIST_CHUNK_COMMAND_ROOT;

public abstract class ListCommandRoot {

    public static final LiteralArgumentBuilder<ServerCommandSource> LIST_COMMAND_ROOT = CommandManager.literal("list")
            .then(LIST_CHUNK_COMMAND_ROOT);
}
