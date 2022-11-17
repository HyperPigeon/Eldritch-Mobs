package net.hyper_pigeon.eldritch_mobs.command.list.chunk;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.hyper_pigeon.eldritch_mobs.enums.LanternType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public abstract class ListChunkCommandRoot {

    public static final LiteralArgumentBuilder<ServerCommandSource> LIST_CHUNK_COMMAND_ROOT = CommandManager.literal("chunk")
            .then(ListChunkCommandFactory.generate(LanternType.SOOTHING))
            .then(ListChunkCommandFactory.generate(LanternType.CURSING_ELITE))
            .then(ListChunkCommandFactory.generate(LanternType.CURSING_ULTRA))
            .then(ListChunkCommandFactory.generate(LanternType.CURSING_ELDRITCH));
}
