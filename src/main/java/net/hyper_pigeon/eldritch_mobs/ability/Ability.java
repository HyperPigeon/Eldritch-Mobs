package net.hyper_pigeon.eldritch_mobs.ability;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.config.AbilitySpecificConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;

public interface Ability {

    String getName();

    default Identifier getIdentifier() { return getIdentifier(getName()); }
    static Identifier getIdentifier(String name) { return EldritchMobsMod.id(name.toLowerCase().replace(' ', '_')); }

    boolean getDisabled();

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
