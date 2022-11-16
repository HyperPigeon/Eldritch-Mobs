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
import net.minecraft.sound.SoundEvents;

public class UndyingAbility implements Ability {


    private final EldritchMobsConfig.UndyingConfig undyingConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.undyingConfig;
    private boolean abilityUsed = false;

    @Override
    public String getName() {
        return undyingConfig.name;
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
    public void onDamaged(LivingEntity entity, DamageSource source, float amount) {
        if (entity.getHealth() <= entity.getMaxHealth() / 2 && !this.abilityUsed) {
            entity.playSound(SoundEvents.ITEM_TOTEM_USE, 1f, 1f);
            entity.setHealth(entity.getMaxHealth());
            entity.clearStatusEffects();
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, undyingConfig.regenerationDuration, undyingConfig.regenerationAmplifier));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, undyingConfig.absorptionDuration, undyingConfig.absorptionAmplifier));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, undyingConfig.fireResistanceDuration, 0));
            entity.world.sendEntityStatus(entity, (byte) 35);

            abilityUsed = true;
        }
    }
}
