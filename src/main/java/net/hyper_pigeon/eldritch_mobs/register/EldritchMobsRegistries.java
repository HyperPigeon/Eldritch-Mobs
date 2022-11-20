package net.hyper_pigeon.eldritch_mobs.register;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.minecraft.util.registry.Registry;

public abstract class EldritchMobsRegistries {

    public static final Registry<Ability> ABILITY_REGISTRY = FabricRegistryBuilder.createSimple(Ability.class, EldritchMobsMod.id("registry/ability")).buildAndRegister();
}
