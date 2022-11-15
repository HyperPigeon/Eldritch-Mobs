package net.hyper_pigeon.eldritch_mobs.ability.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface passiveApplyCallback {

    Event<passiveApplyCallback> PASSIVE_APPLY = EventFactory.createArrayBacked(passiveApplyCallback.class,
            (listeners) -> (mobEntity) -> {
                for (passiveApplyCallback listener : listeners) {
                    ActionResult result = listener.passiveApply(mobEntity);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult passiveApply(LivingEntity livingEntity);
}
