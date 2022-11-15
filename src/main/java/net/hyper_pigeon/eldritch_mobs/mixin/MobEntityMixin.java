package net.hyper_pigeon.eldritch_mobs.mixin;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.rank.MobRank;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity implements ComponentProvider {

    @Shadow
    protected int experiencePoints;

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);

        if (EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank() != MobRank.NONE && EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank() != MobRank.UNDECIDED
                && !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOffBossBars && !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.crosshairBossBars) {
            EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getBossBar().addPlayer(player);
        }

    }

    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);

        if (EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank() != MobRank.NONE
                && EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank() != MobRank.UNDECIDED
                && !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOffBossBars && !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.crosshairBossBars) {
            EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getBossBar().removePlayer(player);
        }
    }

    @Inject(at = @At("HEAD"), method = "getXpToDrop")
    protected void multiplyXpDrop(CallbackInfoReturnable<Integer> cir) {
        if (EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank() != MobRank.NONE && EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank() != MobRank.UNDECIDED) {
            switch (EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank()) {
                case ELITE    -> this.experiencePoints = experiencePoints * EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EliteXpMultiplier;
                case ULTRA    -> this.experiencePoints = experiencePoints * EldritchMobsMod.ELDRITCH_MOBS_CONFIG.UltraXpMultiplier;
                case ELDRITCH -> this.experiencePoints = experiencePoints * EldritchMobsMod.ELDRITCH_MOBS_CONFIG.EldritchXpMultiplier;
                default -> {}
            }
        }
    }
}
