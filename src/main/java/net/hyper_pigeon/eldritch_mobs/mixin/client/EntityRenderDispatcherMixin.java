package net.hyper_pigeon.eldritch_mobs.mixin.client;

import net.hyper_pigeon.eldritch_mobs.extensions.EntityRenderDispatcherExtensions;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin implements EntityRenderDispatcherExtensions {
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
