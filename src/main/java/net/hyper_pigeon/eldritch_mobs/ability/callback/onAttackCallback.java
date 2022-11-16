package net.hyper_pigeon.eldritch_mobs.ability.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface onAttackCallback {
    Event<onAttackCallback> ON_ATTACK = EventFactory.createArrayBacked(onAttackCallback.class,
            (listeners) -> (attacker, target) -> {
                for (onAttackCallback listener : listeners) {
                    ActionResult result = listener.onAttack(attacker, target);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );


    ActionResult onAttack(LivingEntity attacker, LivingEntity target);
}
