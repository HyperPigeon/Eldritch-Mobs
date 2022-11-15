package net.hyper_pigeon.eldritch_mobs.ability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;

public interface Ability {

    String getName();

    static String getConfig() { return null; }

    AbilityType getAbilityType();

    AbilitySubType getAbilitySubType();

    default long getCooldown() {
        return 0;
    }

    default boolean canUseAbility(MobEntity mobEntity) {
        return false;
    }

    default void onAbilityUse(MobEntity mobEntity) {}

    default void passiveApply(MobEntity mobEntity) {}

    default void onDamaged(LivingEntity entity, DamageSource source, float amount) {}

    default boolean dealsSelfDamage() {
        return false;
    }

    default boolean negatesProjectileDamage() {
        return false;
    }

    default void onAttack(LivingEntity attacker, LivingEntity entity) {}

    default void onDamageToTarget(LivingEntity attacker, LivingEntity target, DamageSource source, float amount) {}

    default void onDeath(LivingEntity livingEntity, DamageSource damageSource) {}

    default void onRangedAttack(LivingEntity owner, PersistentProjectileEntity projectile) {}
}
