package net.hyper_pigeon.eldritch_mobs.ability.passive.defensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.tag.DamageTypeTags;

public class DeflectorAbility implements Ability {

    private final EldritchMobsConfig.DeflectorConfig deflectorConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.deflectorConfig;

    @Override
    public String getName() {
        return deflectorConfig.name;
    }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.PASSIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.DEFENSIVE;
    }

    @Override
    public boolean negatesProjectileDamage() {
        return true;
    }

    @Override
    public void onDamaged(LivingEntity entity, DamageSource source, float amount) {
        if (source.isIn(DamageTypeTags.IS_PROJECTILE) && (source.getSource() instanceof PersistentProjectileEntity projectileEntity)) {

            if (projectileEntity.getOwner() != null) {
                Entity owner = projectileEntity.getOwner();
                double targetX = owner.getX();
                double targetY = owner.getY();
                double targetZ = owner.getZ();

                double entityX = entity.getX();
                double entityY = entity.getY();
                double entityZ = entity.getZ();

                double diffX = entityX - targetX;
                double diffY = entityY - targetY;
                double diffZ = entityZ - targetZ;

                projectileEntity.addVelocity(diffX, diffY, diffZ);
            } else {
                projectileEntity.setOwner(entity);
                projectileEntity.setVelocity(10, projectileEntity.getVelocity().getY(), 10);
            }
        }
    }
}
