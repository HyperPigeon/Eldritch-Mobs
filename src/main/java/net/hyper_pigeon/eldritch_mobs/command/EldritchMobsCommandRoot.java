package net.hyper_pigeon.eldritch_mobs.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.hyper_pigeon.eldritch_mobs.command.summon.SummonCommandRoot;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;

import static net.hyper_pigeon.eldritch_mobs.command.list.ListCommandRoot.LIST_COMMAND_ROOT;
import static net.hyper_pigeon.eldritch_mobs.command.summon.SummonCommandRoot.SUMMON_COMMAND_ROOT;

public abstract class EldritchMobsCommandRoot {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {

        CommandNode<ServerCommandSource> commandNode = dispatcher.register(CommandManager.literal("eldritch_mobs")
                .requires(source -> source.hasPermissionLevel(4))
                .then(SUMMON_COMMAND_ROOT)
                .then(LIST_COMMAND_ROOT)
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
