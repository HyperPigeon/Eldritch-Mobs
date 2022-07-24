package net.hyper_pigeon.eldritch_mobs.register;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.component.MobModifierComponent;
import net.minecraft.entity.mob.MobEntity;

public final class EldritchMobsComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(MobEntity.class, EldritchMobsMod.ELDRITCH_MODIFIERS, MobModifierComponent::new);
    }
}
