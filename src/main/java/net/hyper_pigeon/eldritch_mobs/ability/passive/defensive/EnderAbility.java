package net.hyper_pigeon.eldritch_mobs.ability.passive.defensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class EnderAbility implements Ability {

    private final EldritchMobsConfig.EnderConfig enderConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.enderConfig;

    @Override
    public String getName() {
        return enderConfig.name;
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
        if (source.isIn(DamageTypeTags.IS_PROJECTILE) && (source.getSource() instanceof PersistentProjectileEntity)) {
            for (int i = 0; i < 32; ++i) {
                teleportRandomly(entity);
            }
        }
    }

    private void teleportRandomly(LivingEntity livingEntity) {
        if (!livingEntity.world.isClient() && livingEntity.isAlive()) {
            double d = livingEntity.getX() + (livingEntity.getRandom().nextDouble() - 0.5D) * 25.0D;
            double e = livingEntity.getY() + (double) (livingEntity.getRandom().nextInt(64) - 32);
            double f = livingEntity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5D) * 25.0D;
            this.teleportTo(d, e, f, livingEntity);
        }
    }

    private void teleportTo(double x, double y, double z, LivingEntity livingEntity) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

        while (mutable.getY() > 0 && !livingEntity.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockState = livingEntity.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (bl && !bl2) {
            boolean bl3 = livingEntity.teleport(x, y, z, false);
            if (bl3 && !livingEntity.isSilent()) {
                livingEntity.world.playSound(null, livingEntity.prevX, livingEntity.prevY, livingEntity.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, livingEntity.getSoundCategory(), 1.0F, 1.0F);
                livingEntity.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }
}
