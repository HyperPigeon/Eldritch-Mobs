package net.hyper_pigeon.eldritch_mobs.mixin.client;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.hyper_pigeon.eldritch_mobs.extensions.EntityRenderManagerExtensions;
import net.hyper_pigeon.eldritch_mobs.extensions.GameRendererExtensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.util.ObjectAllocator;
import org.joml.Matrix4f;
import org.joml.Vector4f;
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
    private EntityRenderManager entityRenderManager;

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/EntityRenderManager;configure(Lnet/minecraft/client/render/Camera;Lnet/minecraft/entity/Entity;)V"
            )
    )
    private void configureDispatcher(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, Matrix4f positionMatrix, Matrix4f matrix4f, Matrix4f projectionMatrix, GpuBufferSlice fogBuffer, Vector4f fogColor, boolean renderSky, CallbackInfo ci) {
        ((EntityRenderManagerExtensions) this.entityRenderManager).eldritch_mobs$configureTargetedEldritch(((GameRendererExtensions) this.client.gameRenderer).eldritch_mobs$getTargetedEldritch());
    }
}
