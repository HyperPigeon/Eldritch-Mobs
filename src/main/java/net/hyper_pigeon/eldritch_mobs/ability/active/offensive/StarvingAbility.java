package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;

public class StarvingAbility implements Ability {

    private static final EldritchMobsConfig.StarvingConfig STARVING_CONFIG = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.starvingConfig;
    private final long cooldown = STARVING_CONFIG.cooldown;
    private long nextUseTime = 0L;

    @Override
    public String getName() {
        return STARVING_CONFIG.name;
    }

    @Override
    public boolean getDisabled() { return STARVING_CONFIG.disabled; }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.OFFENSIVE;
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public boolean canUseAbility(MobEntity mobEntity) {
        return mobEntity.world.getTime() > nextUseTime && mobEntity.getTarget() != null;
    }

    @Override
    public void onAbilityUse(MobEntity mobEntity) {
        if (mobEntity.getTarget() != null && mobEntity.getTarget().isAlive() && mobEntity.canSee(mobEntity.getTarget())) {
            long time = mobEntity.getEntityWorld().getTime();
            if (time > nextUseTime) {
                nextUseTime = time + cooldown;
                LivingEntity target = mobEntity.getTarget();
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, STARVING_CONFIG.duration, STARVING_CONFIG.amplifier));
            }
        }
    }
}
