package net.hyper_pigeon.eldritch_mobs.ability.passive.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;

public class SpeedsterAbility implements Ability {

    private static final EldritchMobsConfig.SpeedsterConfig SPEEDSTER_CONFIG = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.speedsterConfig;

    @Override
    public String getName() {
        return SPEEDSTER_CONFIG.name;
    }

    @Override
    public boolean getDisabled() { return SPEEDSTER_CONFIG.disabled; }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.PASSIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.OFFENSIVE;
    }

    @Override
    public void passiveApply(MobEntity mobEntity) {
        mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, SPEEDSTER_CONFIG.amplifier));
    }
}
