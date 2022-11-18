package net.hyper_pigeon.eldritch_mobs.register;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.hyper_pigeon.eldritch_mobs.command.EldritchMobsCommandRoot;

public class EldritchMobsCommands {

    public static void init() {
        CommandRegistrationCallback.EVENT.register(EldritchMobsCommandRoot::register);
    }
}
