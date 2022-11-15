package net.hyper_pigeon.eldritch_mobs.ability.passive.defensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class WitheringAbility implements Ability {

    private final EldritchMobsConfig.WitheringConfig witheringConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.witheringConfig;

    @Override
    public String getName() {
        return witheringConfig.name;
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
    public void onDamaged(LivingEntity entity, DamageSource source, float amount) {
        if (source.getAttacker() != null && source.getAttacker().isAlive() && source.getAttacker() instanceof LivingEntity) {
            ((LivingEntity) source.getAttacker()).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, witheringConfig.duration, witheringConfig.amplifier));
        }
    }
}
