package net.hyper_pigeon.eldritch_mobs.ability.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

public interface onDamagedCallback {
    Event<onDamagedCallback> ON_DAMAGED = EventFactory.createArrayBacked(onDamagedCallback.class,
            (listeners) -> (mobEntity, source, amount) -> {
                for (onDamagedCallback listener : listeners) {
                    ActionResult result = listener.onDamaged(mobEntity, source, amount);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );


    ActionResult onDamaged(LivingEntity entity, DamageSource source, float amount);
}
