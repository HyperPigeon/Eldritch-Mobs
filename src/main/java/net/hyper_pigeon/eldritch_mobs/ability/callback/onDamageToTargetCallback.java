package net.hyper_pigeon.eldritch_mobs.ability.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

public interface onDamageToTargetCallback {
    Event<onDamageToTargetCallback> ON_DAMAGE_TO_TARGET = EventFactory.createArrayBacked(onDamageToTargetCallback.class,
            (listeners) -> (attacker, target, source, amount) -> {
                for (onDamageToTargetCallback listener : listeners) {
                    ActionResult result = listener.onDamageToTarget(attacker, target, source, amount);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );


    ActionResult onDamageToTarget(LivingEntity attacker, LivingEntity target, DamageSource source, float amount);
}
