package net.hyper_pigeon.eldritch_mobs.register;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public class EldritchMobsAttributeModifiers {
    public static final EntityAttributeModifier ELITE_HEALTH_BOOST = new EntityAttributeModifier("ELITE_HEALTH_BOOST", EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EliteHealthMultiplier,
            EntityAttributeModifier.Operation.fromId(1));

    public static final EntityAttributeModifier ULTRA_HEALTH_BOOST = new EntityAttributeModifier("ULTRA_HEALTH_BOOST", EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraHealthMultiplier,
            EntityAttributeModifier.Operation.fromId(1));

    public static final EntityAttributeModifier ELDRITCH_HEALTH_BOOST = new EntityAttributeModifier("ELDRITCH_HEALTH_BOOST", EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchHealthMultiplier,
            EntityAttributeModifier.Operation.fromId(1));
}
