package net.hyper_pigeon.eldritch_mobs.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.extensions.EntityRenderDispatcherExtensions;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(MobEntityRenderer.class)
public class MobEntityRendererMixin {
    @WrapOperation(
            method = "hasLabel(Lnet/minecraft/entity/mob/MobEntity;)Z",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;targetedEntity:Lnet/minecraft/entity/Entity;"
            )
    )
    private Entity replaceTargetedEntity(EntityRenderDispatcher instance, Operation<Entity> original) {
        if (EldritchMobsMod.ELDRITCH_MOBS_CONFIG.distantVisibleNameTags) {
			var eldritch = ((EntityRenderDispatcherExtensions) instance).eldritch_mobs$getTargetedEldritch();
			if (eldritch != null) {
				return eldritch;
			}
        }

        return original.call(instance);
    }
}
