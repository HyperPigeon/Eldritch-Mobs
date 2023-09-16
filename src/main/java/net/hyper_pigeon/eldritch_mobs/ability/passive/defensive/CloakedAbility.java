package net.hyper_pigeon.eldritch_mobs.ability.passive.defensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;

public class CloakedAbility implements Ability {

    private final EldritchMobsConfig.CloakedConfig cloakedConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.cloakedConfig;

    @Override
    public String getName() {
        return cloakedConfig.name;
    }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.DEFENSIVE;
    }

    @Override
    public void passiveApply(MobEntity mobEntity) {
        if(!mobEntity.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10000));
        }
    }
}
