package net.hyper_pigeon.eldritch_mobs.ability.passive.offensive;

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



public class BerserkAbility implements Ability {

    private final EldritchMobsConfig.BerserkConfig berserkConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.berserkConfig;


    @Override
    public String getName() {
        return berserkConfig.name;
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
    public boolean dealsSelfDamage() {
        return true;
    }

    @Override
    public void passiveApply(MobEntity mobEntity) {
        if(!mobEntity.hasStatusEffect(StatusEffects.STRENGTH)) {
            mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 10000, berserkConfig.amplifier));
        }
    }

    @Override
    public void onDamaged(LivingEntity entity, DamageSource source, float amount) {
        entity.setHealth(entity.getHealth() - amount);
    }

}
