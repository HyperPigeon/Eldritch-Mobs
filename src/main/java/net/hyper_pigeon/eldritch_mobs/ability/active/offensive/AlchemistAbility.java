package net.hyper_pigeon.eldritch_mobs.ability.active.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AlchemistAbility implements Ability {

    private final EldritchMobsConfig.AlchemistConfig ALCHEMIST_CONFIG = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.alchemistConfig;
    private final long cooldown = ALCHEMIST_CONFIG.cooldown;
    private long nextUseTime = 0;


    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public String getName() {
        return ALCHEMIST_CONFIG.name;
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

    @Override
    public void onAbilityUse(MobEntity mobEntity) {
        if (!mobEntity.getEntityWorld().isClient() && canUseAbility(mobEntity)) {
            LivingEntity target = mobEntity.getTarget();

            if (target != null) {
                Vec3d vec3d = target.getVelocity();
                double d = target.getX() + vec3d.x - mobEntity.getX();
                double e = target.getEyeY() - 1.100000023841858D - mobEntity.getY();
                double f = target.getZ() + vec3d.z - mobEntity.getZ();
                float g = MathHelper.sqrt((float) (d * d + f * f));
                Potion potion = target.isUndead()
                        ? (ALCHEMIST_CONFIG.useStrongHealing ? Potions.STRONG_HEALING : Potions.HEALING)
                        : (ALCHEMIST_CONFIG.useStrongHarming ? Potions.STRONG_HARMING : Potions.HARMING);
                PotionEntity potionEntity = new PotionEntity(mobEntity.world, mobEntity);
                potionEntity.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
                potionEntity.setPitch(potionEntity.getPitch() - 20);
                potionEntity.setVelocity(d, e + (double) (g * 0.2F), f, 0.25F, 8.0F);
                mobEntity.world.spawnEntity(potionEntity);
                nextUseTime = getCooldown() + mobEntity.world.getTime();
            }
        }
    }
}
