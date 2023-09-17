package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;

public class DrowningAbility implements Ability {

    private final EldritchMobsConfig.DrowningConfig drowningConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.drowningConfig;

    @Override
    public String getName() {
        return drowningConfig.name;
    }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.OFFENSIVE;
    }

    @Override
    public void onAbilityUse(MobEntity mobEntity) {
        if (!mobEntity.getEntityWorld().isClient() && mobEntity.getTarget() != null && mobEntity.canSee(mobEntity.getTarget()) && mobEntity.getTarget().isAlive()) {
            LivingEntity target = mobEntity.getTarget();
            if (!(target.hasStatusEffect(StatusEffects.WATER_BREATHING))) {
                target.damage(mobEntity.getDamageSources().drown(), drowningConfig.drowningDamage);
            }
        }
    }

    @Override
    public void onDamaged(LivingEntity entity, DamageSource source, float amount) {
        if (source.getAttacker() != null && source.getAttacker() instanceof LivingEntity) {
            ((LivingEntity) source.getAttacker()).addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, drowningConfig.waterBreathingDuration));
        }
    }
}
