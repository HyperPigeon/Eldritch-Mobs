package net.hyper_pigeon.eldritch_mobs.register;

import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.command.argument_type.LanternArgumentType;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;

public abstract class EldritchMobsArgumentTypes {

    public static void init() {
        ArgumentTypeRegistry.registerArgumentType(
                EldritchMobsMod.id("lantern"),
                LanternArgumentType.class,
                ConstantArgumentSerializer.of(LanternArgumentType::lantern)
        );
    }
}
