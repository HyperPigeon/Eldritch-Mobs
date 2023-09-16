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

    private final EldritchMobsConfig.SpeedsterConfig speedsterConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.speedsterConfig;

    @Override
    public String getName() {
        return speedsterConfig.name;
    }

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
        if(!mobEntity.hasStatusEffect(StatusEffects.SPEED)) {
            mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10000, speedsterConfig.amplifier));
        }
    }
}
