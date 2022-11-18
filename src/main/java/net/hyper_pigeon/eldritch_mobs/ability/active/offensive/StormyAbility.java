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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.Optional;

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
        if (mobEntity.getTarget() != null && mobEntity.canSee(mobEntity.getTarget()) && mobEntity.getTarget().isAlive()) {
            long time = mobEntity.getEntityWorld().getTime();
            if (time > nextUseTime) {
                nextUseTime = time + cooldown;
                LivingEntity target = mobEntity.getTarget();
                LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, mobEntity.getEntityWorld());

                World world = mobEntity.getEntityWorld();
                Random random = world.getRandom();

                BlockPos lightningEntityPos = new BlockPos(
                        target.getX() + random.nextInt(3),
                        target.getY() + random.nextInt(3),
                        target.getZ() + random.nextInt(3)
                );

                // TODO: Test!
                // If there is a lightning rod nearby and the configs allow redirection, spawn lightning there instead.
                if (stormyConfig.lightningRodRadius >= 0 && !world.isClient) {
                    Optional<BlockPos> lightningRodPos = getLightningRodPos((ServerWorld) world, target.getBlockPos(), stormyConfig.lightningRodRadius);
                    if (lightningRodPos.isPresent()) lightningEntityPos = lightningRodPos.get();
                }

                lightningEntity.setPos(lightningEntityPos.getX(), lightningEntityPos.getY(), lightningEntityPos.getZ());
                world.spawnEntity(lightningEntity);
            }
        }
    }

    private Optional<BlockPos> getLightningRodPos(ServerWorld world, BlockPos pos, int radius) {
        return world.getPointOfInterestStorage().getNearestPosition(
                registryEntry -> registryEntry.matchesKey(PointOfInterestTypes.LIGHTNING_ROD),
                posX -> posX.getY() == world.getTopY(Heightmap.Type.WORLD_SURFACE, posX.getX(), posX.getZ()) - 1,
                pos,
                radius,
                PointOfInterestStorage.OccupationStatus.ANY
        ).map((posX) -> posX.up(1));
    }
}
