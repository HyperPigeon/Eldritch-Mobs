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

    private final EldritchMobsConfig.ResistantConfig resistantConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.resistantConfig;

    @Override
    public String getName() {
        return resistantConfig.name;
    }

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
        if(!mobEntity.hasStatusEffect(StatusEffects.RESISTANCE)) {
            mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10000, resistantConfig.amplifier));
        }

    }
}
