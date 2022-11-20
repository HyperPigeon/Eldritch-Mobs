package net.hyper_pigeon.eldritch_mobs.ability.passive.defensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;

public class ResistantAbility implements Ability {

    private static final EldritchMobsConfig.ResistantConfig RESISTANT_CONFIG = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.resistantConfig;

    @Override
    public String getName() {
        return RESISTANT_CONFIG.name;
    }

    @Override
    public boolean getDisabled() { return RESISTANT_CONFIG.disabled; }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.PASSIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.DEFENSIVE;
    }

    @Override
    public void passiveApply(MobEntity mobEntity) {
        mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, RESISTANT_CONFIG.amplifier));
    }
}
