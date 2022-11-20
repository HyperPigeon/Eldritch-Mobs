package net.hyper_pigeon.eldritch_mobs.ability.passive.defensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.mob.MobEntity;

public class RegeneratingAbility implements Ability {

    private static final EldritchMobsConfig.RegeneratingConfig REGENERATING_CONFIG = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.regeneratingConfig;

    @Override
    public String getName() {
        return REGENERATING_CONFIG.name;
    }

    @Override
    public boolean getDisabled() { return REGENERATING_CONFIG.disabled; }

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
        mobEntity.heal(REGENERATING_CONFIG.healAmount);
    }
}
