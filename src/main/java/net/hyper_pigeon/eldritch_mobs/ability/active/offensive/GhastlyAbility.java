package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.Vec3d;

public class GhastlyAbility implements Ability {

    private final EldritchMobsConfig.GhastlyConfig ghastlyConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.ghastlyConfig;
    private final long cooldown = ghastlyConfig.cooldown;
    private long nextUseTime = 0L;

    @Override
    public String getName() {
        return ghastlyConfig.name;
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
                double e = 4.0D;
                Vec3d vec3d = mobEntity.getRotationVec(1.0F);
                double f = target.getX() - (mobEntity.getX() + vec3d.x * 4.0D);
                double g = target.getBodyY(0.5D) - (0.5D + mobEntity.getBodyY(0.5D));
                double h = target.getZ() - (mobEntity.getZ() + vec3d.z * 4.0D);
                if (!mobEntity.isSilent()) {
                    mobEntity.getEntityWorld().syncWorldEvent(null, 1016, mobEntity.getBlockPos(), 0);
                }

                FireballEntity fireballEntity;
                fireballEntity = new FireballEntity(mobEntity.getEntityWorld(), mobEntity, f, g, h, ghastlyConfig.fireballPower);

                fireballEntity.updatePosition(mobEntity.getX() + vec3d.x * 4.0D, mobEntity.getBodyY(0.5D) + 0.5D, fireballEntity.getZ() + vec3d.z * 4.0D);
                mobEntity.getEntityWorld().spawnEntity(fireballEntity);
            }
        }
    }
}
