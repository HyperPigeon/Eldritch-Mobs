package net.hyper_pigeon.eldritch_mobs.register;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.hyper_pigeon.eldritch_mobs.command.SummonEldritchCommand;
import net.hyper_pigeon.eldritch_mobs.command.SummonEliteCommand;
import net.hyper_pigeon.eldritch_mobs.command.SummonUltraCommand;

public class EldritchMobsCommands {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SummonEliteCommand.register(dispatcher,registryAccess);
        }
        );
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SummonUltraCommand.register(dispatcher,registryAccess);
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SummonEldritchCommand.register(dispatcher,registryAccess);
        });
    }
}
