package net.hyper_pigeon.eldritch_mobs.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BossBarHud.class)
public abstract class BossBarHudMixin {

    @Shadow
    @Final
    private MinecraftClient client;

//    @Inject(method = "render", at = @At(value = "INVOKE",target = "net/minecraft/client/font/TextRenderer.drawWithShadow (Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I", opcode = Opcodes.INVOKEVIRTUAL,ordinal = 0, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
//    private void renderBottomText(MatrixStack matrices, CallbackInfo ci, int i, int j, Iterator var4, ClientBossBar clientBossBar, int k, int l, Text text, int m, int n, int o){
//    }

}
