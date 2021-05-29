package net.hyper_pigeon.eldritch_mobs.mod_components;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.mod_components.modifiers.ModifierComponent;
import net.minecraft.entity.LivingEntity;

public final class EldritchMobsComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, EldritchMobsMod.ELDRITCH_MODIFIERS, ModifierComponent::new);
    }
}