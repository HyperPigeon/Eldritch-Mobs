package net.hyper_pigeon.eldritch_mobs.mixin;

import com.mojang.authlib.GameProfile;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.rank.MobRank;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }


    //mixin into player tick and check if the server player entity is looking at a buffed entity
    @Inject(method = "tick", at = @At("TAIL"))
    public void isLookingAtBuffedEntity(CallbackInfo ci) {
        if (EldritchMobsMod.ELDRITCH_MOBS_CONFIG.crosshairBossBars && !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOffBossBars) {
            Vec3d vec3d = getEyePos();
            Vec3d vec3d2 = getRotationVec(1.0F);
            Vec3d vec3d3 = vec3d.add(vec3d2.x * 100.0D, vec3d2.y * 100.0D, vec3d2.z * 100.0D);
            EntityHitResult entityHitResult = ProjectileUtil.getEntityCollision(
                    world,
                    (ServerPlayerEntity) (Object) this,
                    vec3d,
                    vec3d3,
                    new Box(vec3d, vec3d3).expand(1.0D),
                    (entityX) -> !entityX.isSpectator()
                            && entityX instanceof MobEntity
                            && EldritchMobsMod.getRank((ComponentProvider) entityX) != MobRank.NONE
                            && EldritchMobsMod.getRank((ComponentProvider) entityX) != MobRank.UNDECIDED,
                    0.0F
            );
            if (entityHitResult != null && entityHitResult.getType() == HitResult.Type.ENTITY) {
                MobEntity mobEntity = (MobEntity) entityHitResult.getEntity();
                EldritchMobsMod.ELDRITCH_MODIFIERS.get(mobEntity).getBossBar().addPlayer((ServerPlayerEntity) (Object) this);
            }
        }
    }
}
