package net.hyper_pigeon.eldritch_mobs.ability.passive.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class LifestealAbility implements Ability {

    private static final EldritchMobsConfig.LifestealConfig LIFESTEAL_CONFIG = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.lifestealConfig;

    @Override
    public String getName() {
        return LIFESTEAL_CONFIG.name;
    }

    @Override
    public boolean getDisabled() { return LIFESTEAL_CONFIG.disabled; }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.PASSIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.OFFENSIVE;
    }

    @Override
    public void onDamageToTarget(LivingEntity attacker, LivingEntity target, DamageSource source, float amount) {
        attacker.heal((float) (amount * LIFESTEAL_CONFIG.lifestealHealProportion));
    }
}
