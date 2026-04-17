package net.hyper_pigeon.eldritch_mobs.extensions;

import net.minecraft.entity.Entity;

public interface EntityRenderManagerExtensions {
    void eldritch_mobs$configureTargetedEldritch(Entity target);

    Entity eldritch_mobs$getTargetedEldritch();
}
