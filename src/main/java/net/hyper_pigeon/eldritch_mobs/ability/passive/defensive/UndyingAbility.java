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


    private static final EldritchMobsConfig.UndyingConfig UNDYING_CONFIG = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.undyingConfig;
    private boolean abilityUsed = false;

    @Override
    public String getName() {
        return UNDYING_CONFIG.name;
    }

    @Override
    public boolean getDisabled() { return UNDYING_CONFIG.disabled; }

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
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, UNDYING_CONFIG.regenerationDuration, UNDYING_CONFIG.regenerationAmplifier));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, UNDYING_CONFIG.absorptionDuration, UNDYING_CONFIG.absorptionAmplifier));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, UNDYING_CONFIG.fireResistanceDuration, 0));
            entity.world.sendEntityStatus(entity, (byte) 35);

            abilityUsed = true;
        }
    }
}
