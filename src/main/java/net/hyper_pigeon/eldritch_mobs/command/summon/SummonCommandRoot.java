package net.hyper_pigeon.eldritch_mobs.command.summon;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.hyper_pigeon.eldritch_mobs.enums.MobRank;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public abstract class SummonCommandRoot {

    public static final LiteralArgumentBuilder<ServerCommandSource> SUMMON_COMMAND_ROOT = CommandManager.literal("summon")
            .requires(source -> source.hasPermissionLevel(4))
            .then(SummonCommandFactory.generate(MobRank.ELITE))
            .then(SummonCommandFactory.generate(MobRank.ULTRA))
            .then(SummonCommandFactory.generate(MobRank.ELDRITCH));
}
