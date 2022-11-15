package net.hyper_pigeon.eldritch_mobs.ability.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.ActionResult;

public interface onRangedAttackCallback {
    Event<onRangedAttackCallback> ON_RANGED_ATTACK = EventFactory.createArrayBacked(onRangedAttackCallback.class,
            (listeners) -> (owner, projectile) -> {
                for (onRangedAttackCallback listener : listeners) {
                    ActionResult result = listener.onRangedAttack(owner, projectile);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );


    ActionResult onRangedAttack(LivingEntity owner, PersistentProjectileEntity projectile);
}
