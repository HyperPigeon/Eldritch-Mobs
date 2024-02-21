package net.hyper_pigeon.eldritch_mobs.extensions;

import net.minecraft.entity.Entity;

public interface GameRendererExtensions {
    void eldritch_mobs$updateTargetedEldritch(float tickDelta);

    Entity eldritch_mobs$getTargetedEldritch();
}
