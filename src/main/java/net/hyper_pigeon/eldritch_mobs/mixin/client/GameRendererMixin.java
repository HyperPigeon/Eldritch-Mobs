package net.hyper_pigeon.eldritch_mobs.mixin.client;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.extensions.GameRendererExtensions;
import net.hyper_pigeon.eldritch_mobs.rank.MobRank;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin implements GameRendererExtensions {
	@Unique
	Entity targetedEldritch;

	@Shadow
	@Final
	MinecraftClient client;

	@Inject(
			method = "renderWorld",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;",
					ordinal = 0
			)
	)
	private void injectUpdateOnGameRenderer(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
		this.eldritch_mobs$updateTargetedEldritch(tickDelta);
	}

	@Override
	public void eldritch_mobs$updateTargetedEldritch(float tickDelta) {
		if (this.client.targetedEntity == null) {
			var camera = this.client.getCameraEntity();
			this.targetedEldritch = null;
			if (camera != null && this.client.world != null) {
				var cameraVec = camera.getCameraPosVec(tickDelta);
				var rotationVec = camera.getRotationVec(1.0F);
				var reachVec = cameraVec.add(rotationVec.multiply(64.0));
				var box = camera.getBoundingBox().stretch(rotationVec.multiply(64.0)).expand(1.0);

				var hitResult = ProjectileUtil.raycast(camera, cameraVec, reachVec, box, entity -> !entity.isSpectator()
                        && entity.canHit()
						&& entity instanceof MobEntity
                        && EldritchMobsMod.getRank((ComponentProvider) entity) != MobRank.NONE
                        && EldritchMobsMod.getRank((ComponentProvider) entity) != MobRank.UNDECIDED, 4096.0);
				if (hitResult != null) {
					this.targetedEldritch = hitResult.getEntity();
				}
			}
		}
	}

	@Override
	public Entity eldritch_mobs$getTargetedEldritch() {
		return this.targetedEldritch;
	}
}
