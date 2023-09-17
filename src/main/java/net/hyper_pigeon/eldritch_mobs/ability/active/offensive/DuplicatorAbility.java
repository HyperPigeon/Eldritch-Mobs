package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public class DuplicatorAbility implements Ability {

    private final EldritchMobsConfig.DuplicatorConfig duplicatorConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.duplicatorConfig;
    private final long cooldown = duplicatorConfig.cooldown;
    private long nextUseTime = 0;

    @Override
    public String getName() {
        return duplicatorConfig.name;
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
    public boolean canUseAbility(MobEntity mobEntity) {
        return mobEntity.world.getTime() > nextUseTime && mobEntity.getTarget() != null;
    }

    public void onAbilityUse(MobEntity entity) {
        if (!entity.getEntityWorld().isClient() && entity.getTarget() != null && entity.canSee(entity.getTarget()) && entity.getTarget().isAlive()) {
            long time = entity.getEntityWorld().getTime();
            if (time > nextUseTime) {
                nextUseTime = time + cooldown;
                entity.getType().spawn((ServerWorld) entity.getEntityWorld(), entity.getBlockPos(), SpawnReason.REINFORCEMENT);
            }
        }
    }


}
