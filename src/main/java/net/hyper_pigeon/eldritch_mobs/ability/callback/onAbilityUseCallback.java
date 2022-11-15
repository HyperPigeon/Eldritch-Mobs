package net.hyper_pigeon.eldritch_mobs.ability.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface onAbilityUseCallback {

    Event<onAbilityUseCallback> ON_ABILITY_USE = EventFactory.createArrayBacked(onAbilityUseCallback.class,
            (listeners) -> (mobEntity) -> {
                for (onAbilityUseCallback listener : listeners) {
                    ActionResult result = listener.onAbilityUse(mobEntity);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );


    ActionResult onAbilityUse(LivingEntity livingEntity);

}
