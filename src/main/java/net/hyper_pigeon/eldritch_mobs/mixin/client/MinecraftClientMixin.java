package net.hyper_pigeon.eldritch_mobs.mixin.client;

import net.hyper_pigeon.eldritch_mobs.extensions.GameRendererExtensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Final
    @Shadow
    public GameRenderer gameRenderer;

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/tutorial/TutorialManager;tick(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/util/hit/HitResult;)V"
            )
    )
    private void tickTargetedEldritch(CallbackInfo ci) {
        ((GameRendererExtensions) this.gameRenderer).eldritch_mobs$updateTargetedEldritch(1.0F);
    }
}
