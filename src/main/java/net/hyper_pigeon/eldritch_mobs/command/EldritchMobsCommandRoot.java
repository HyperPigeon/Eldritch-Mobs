package net.hyper_pigeon.eldritch_mobs.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.hyper_pigeon.eldritch_mobs.command.summon.SummonCommandRoot;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class EldritchMobsCommandRoot {

    private EldritchMobsCommandRoot() { throw new AssertionError("Class should not be instantiated!"); }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {

        CommandNode<ServerCommandSource> commandNode = dispatcher.register(CommandManager.literal("eldritch_mobs")
                .requires(source -> source.hasPermissionLevel(4))
                .then(SummonCommandRoot.SUMMON_COMMAND_ROOT)
        );

        new ArrayList<String>() {{
            add("eldritch_mobs");
            add("em");
            //noinspection SpellCheckingInspection
            add("eldritchmobs");
            add("eldritch");
        }}.forEach(alias -> dispatcher.register(CommandManager.literal(alias).redirect(commandNode)));
    }
}
