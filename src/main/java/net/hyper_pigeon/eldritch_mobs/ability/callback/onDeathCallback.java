package net.hyper_pigeon.eldritch_mobs.ability.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

public interface onDeathCallback {
    Event<onDeathCallback> ON_DEATH = EventFactory.createArrayBacked(onDeathCallback.class,
            (listeners) -> (livingEntity, damageSource) -> {
                for (onDeathCallback listener : listeners) {
                    ActionResult result = listener.onDeath(livingEntity, damageSource);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );


    ActionResult onDeath(LivingEntity livingEntity, DamageSource damageSource);
}
