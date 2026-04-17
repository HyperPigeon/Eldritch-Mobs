package net.hyper_pigeon.eldritch_mobs.mixin.client;

import net.hyper_pigeon.eldritch_mobs.extensions.EntityRenderManagerExtensions;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderManager.class)
public class EntityRenderManagerMixin implements EntityRenderManagerExtensions {
    @Unique
    private Entity targetedEldritch;

    @Override
    public void eldritch_mobs$configureTargetedEldritch(Entity target) {
        this.targetedEldritch = target;
    }

    @Override
    public Entity eldritch_mobs$getTargetedEldritch() {
        return this.targetedEldritch;
    }
}
