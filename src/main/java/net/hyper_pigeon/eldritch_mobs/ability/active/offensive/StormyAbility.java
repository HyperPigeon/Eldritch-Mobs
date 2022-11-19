package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;

import java.util.Random;

public class StormyAbility implements Ability {

    private final EldritchMobsConfig.StormyConfig stormyConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.stormyConfig;
    private final long cooldown = stormyConfig.cooldown;
    private long nextUseTime = 0;

    @Override
    public String getName() {
        return stormyConfig.name;
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
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public boolean canUseAbility(MobEntity mobEntity) {
        return mobEntity.world.getTime() > nextUseTime && mobEntity.getTarget() != null;
    }

    @Override
    public void onAbilityUse(MobEntity mobEntity) {
        if (!mobEntity.getEntityWorld().isClient() && mobEntity.getTarget() != null && mobEntity.canSee(mobEntity.getTarget()) && mobEntity.getTarget().isAlive()) {
            long time = mobEntity.getEntityWorld().getTime();
            if (time > nextUseTime) {
                nextUseTime = time + cooldown;
                LivingEntity target = mobEntity.getTarget();
                LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, mobEntity.getEntityWorld());

                Random random = new Random();
                lightningEntity.setPos(target.getX() + random.nextInt(3), target.getY() + random.nextInt(3), target.getZ() + random.nextInt(3));
                mobEntity.getEntityWorld().spawnEntity(lightningEntity);
            }
        }
    }
}
