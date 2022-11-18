package net.hyper_pigeon.eldritch_mobs.command.mark.global;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public abstract class MarkGlobalCommandRoot {

    public static final LiteralArgumentBuilder<ServerCommandSource> MARK_GLOBAL_COMMAND_ROOT = CommandManager.literal("mark")
            .requires(source -> source.hasPermissionLevel(4));
            // TODO: Implement
}
