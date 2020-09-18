package net.hyper_pigeon.eldritch_mobs.mixin;

import nerdhub.cardinal.components.api.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin extends HostileEntity   {


    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(at = @At("HEAD"), method = "explode")
    public void removeEffects(CallbackInfo ci){
        if (!this.world.isClient && EldritchMobsMod.isElite((ComponentProvider) this)) {
            this.removeStatusEffect(StatusEffects.HEALTH_BOOST);
            this.removeStatusEffect(StatusEffects.INVISIBILITY);
            this.removeStatusEffect(StatusEffects.SPEED);
            this.removeStatusEffect(StatusEffects.STRENGTH);
        }

    }


}
