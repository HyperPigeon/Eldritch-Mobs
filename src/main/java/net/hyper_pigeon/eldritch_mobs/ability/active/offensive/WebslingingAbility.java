package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
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
    public boolean canUseAbilty(MobEntity mobEntity) {
        if(mobEntity.world.getTime() > nextUseTime && mobEntity.getTarget() != null){
            return true;
        }
        return false;
    }

    @Override
    public void onAbilityUse(MobEntity mobEntity) {
        if(mobEntity.getTarget() != null && mobEntity.canSee(mobEntity.getTarget()) && mobEntity.getTarget().isAlive()) {
            long time = mobEntity.getEntityWorld().getTime();
            if (time > nextUseTime) {
                nextUseTime = time + cooldown;
                LivingEntity target = mobEntity.getTarget();

                double x = target.getX();
                double y = target.getY()+1;
                double z = target.getZ();

                if(target.getEntityWorld().getBlockState(new BlockPos(x,y,z)).getBlock().equals(Blocks.AIR) ||
                        target.getEntityWorld().getBlockState(new BlockPos(x,y,z)).getBlock().equals(Blocks.CAVE_AIR)
                        || target.getEntityWorld().getBlockState(new BlockPos(x,y,z)).getBlock().equals(Blocks.VOID_AIR)) {
                    target.getEntityWorld().setBlockState(new BlockPos(x,y,z), Blocks.COBWEB.getDefaultState());
                }

            }
        }
    }

}
