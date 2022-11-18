package net.hyper_pigeon.eldritch_mobs.command.mark.chunk;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public abstract class MarkChunkCommandRoot {

    public static final LiteralArgumentBuilder<ServerCommandSource> MARK_CHUNK_COMMAND_ROOT = CommandManager.literal("mark")
            .requires(source -> source.hasPermissionLevel(4));
            // TODO: Implement
}
