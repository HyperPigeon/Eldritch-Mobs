package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class GravityAbility implements Ability {

    private final EldritchMobsConfig.GravityConfig gravityConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.gravityConfig;
    private final long cooldown = gravityConfig.cooldown;
    private long nextUseTime = 0;

    @Override
    public String getName() {
        return gravityConfig.name;
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
                double targetX = target.getX();
                double targetY = target.getY();
                double targetZ = target.getZ();
                double entityX = mobEntity.getX();
                double entityY = mobEntity.getY();
                double entityZ = mobEntity.getZ();
                double diffX = entityX - targetX;
                double diffY = entityY - targetY;
                double diffZ = entityZ - targetZ;

                if (target instanceof ServerPlayerEntity serverTarget) serverTarget.networkHandler.sendPacket(
                        new EntityVelocityUpdateS2CPacket(target.getId(), new Vec3d(diffX, diffY, diffZ))
                );
                else target.addVelocity(diffX, diffY, diffZ);
            }
        }
    }
}
