package net.hyper_pigeon.eldritch_mobs.ability.active.defensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;

public class SprinterAbility implements Ability {

    private static final EldritchMobsConfig.SprinterConfig SPRINTER_CONFIG = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.sprinterConfig;
    private final long cooldown = SPRINTER_CONFIG.cooldown;
    private long nextUseTime = 0L;

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public String getName() {
        return SPRINTER_CONFIG.name;
    }

    @Override
    public boolean getDisabled() { return SPRINTER_CONFIG.disabled; }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.DEFENSIVE;
    }

    @Override
    public boolean canUseAbility(MobEntity mobEntity) {
        return mobEntity.getWorld().getTime() > nextUseTime;
    }

    @Override
    public void onAbilityUse(MobEntity mobEntity) {
        if (!mobEntity.getEntityWorld().isClient() && canUseAbility(mobEntity)) {
            mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, SPRINTER_CONFIG.duration, SPRINTER_CONFIG.amplifier));
            nextUseTime = mobEntity.world.getTime() + getCooldown();
        }
    }
}
