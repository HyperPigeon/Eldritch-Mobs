package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;

public class WebslingingAbility implements Ability {

    private final EldritchMobsConfig.WebslingingConfig webslingingConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.webslingingConfig;
    private final long cooldown = webslingingConfig.cooldown;
    private long nextUseTime = 0L;

    @Override
    public String getName() {
        return webslingingConfig.name;
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

                int x = (int) target.getX();
                int y = (int) (target.getY() + 1);
                int z = (int) target.getZ();

                BlockPos blockPos = new BlockPos(x,y,z);

                if (target.getEntityWorld().getBlockState(blockPos).getBlock().equals(Blocks.AIR) ||
                        target.getEntityWorld().getBlockState(blockPos).getBlock().equals(Blocks.CAVE_AIR)
                        || target.getEntityWorld().getBlockState(blockPos).getBlock().equals(Blocks.VOID_AIR)) {
                    target.getEntityWorld().setBlockState(blockPos, Blocks.COBWEB.getDefaultState());
                }

            }
        }
    }

}
