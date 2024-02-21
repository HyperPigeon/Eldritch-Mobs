package net.hyper_pigeon.eldritch_mobs.mixin.client;

import net.hyper_pigeon.eldritch_mobs.extensions.EntityRenderDispatcherExtensions;
import net.hyper_pigeon.eldritch_mobs.extensions.GameRendererExtensions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    @Final
    private EntityRenderDispatcher entityRenderDispatcher;

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;configure(Lnet/minecraft/world/World;Lnet/minecraft/client/render/Camera;Lnet/minecraft/entity/Entity;)V"
            )
    )
    private void configureDispatcher(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        ((EntityRenderDispatcherExtensions) this.entityRenderDispatcher).eldritch_mobs$configureTargetedEldritch(((GameRendererExtensions) gameRenderer).eldritch_mobs$getTargetedEldritch());
    }
}
