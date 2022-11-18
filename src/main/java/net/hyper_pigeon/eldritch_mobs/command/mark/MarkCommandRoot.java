package net.hyper_pigeon.eldritch_mobs.command.mark;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static net.hyper_pigeon.eldritch_mobs.command.mark.chunk.MarkChunkCommandRoot.MARK_CHUNK_COMMAND_ROOT;
import static net.hyper_pigeon.eldritch_mobs.command.mark.dimension.MarkDimensionCommandRoot.MARK_DIMENSION_COMMAND_ROOT;
import static net.hyper_pigeon.eldritch_mobs.command.mark.global.MarkGlobalCommandRoot.MARK_GLOBAL_COMMAND_ROOT;

public abstract class MarkCommandRoot {

    public static final LiteralArgumentBuilder<ServerCommandSource> MARK_COMMAND_ROOT = CommandManager.literal("mark")
            .requires(source -> source.hasPermissionLevel(4))
            .then(MARK_CHUNK_COMMAND_ROOT)
            .then(MARK_DIMENSION_COMMAND_ROOT)
            .then(MARK_GLOBAL_COMMAND_ROOT);
}
