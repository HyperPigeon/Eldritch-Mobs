package net.hyper_pigeon.eldritch_mobs.mixin;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends Entity {

    @Shadow
    private double damage;

    public PersistentProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;)V", at = @At("RETURN"))
    private void onInit(EntityType type, LivingEntity owner, World world, CallbackInfo callbackInfo) {
        if(EldritchMobsMod.ELDRITCH_MODIFIERS.get(owner).hasMod("sniper")){
            damage = damage*2;
        }
    }
}
