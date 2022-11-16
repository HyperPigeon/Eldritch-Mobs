package net.hyper_pigeon.eldritch_mobs.register;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.callback.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.ActionResult;

import java.util.List;

public class EldritchMobsEventListeners {
    public static void init() {
        onAttackCallback.ON_ATTACK.register((attacker, target) -> {
            if (attacker instanceof MobEntity) {
                List<Ability> abilityList = EldritchMobsMod.getModifiers((ComponentProvider) attacker);

                for (Ability ability : abilityList) {
                    ability.onAttack(attacker, target);
                }

            }
            return ActionResult.PASS;
        });

        onDamagedCallback.ON_DAMAGED.register((livingEntity, source, amount) -> {
            if (livingEntity instanceof MobEntity) {
                List<Ability> abilityList = EldritchMobsMod.getModifiers((ComponentProvider) livingEntity);
                for (Ability ability : abilityList) {
                    ability.onDamaged(livingEntity, source, amount);
                }
            }
            return ActionResult.PASS;
        });

        onDamageToTargetCallback.ON_DAMAGE_TO_TARGET.register((attacker, target, source, amount) -> {
            if (attacker instanceof MobEntity) {
                List<Ability> abilityList = EldritchMobsMod.getModifiers((ComponentProvider) attacker);

                for (Ability ability : abilityList) {
                    ability.onDamageToTarget(attacker, target, source, amount);
                }

            }
            return ActionResult.PASS;
        });

        passiveApplyCallback.PASSIVE_APPLY.register((livingEntity) ->
        {
            if (livingEntity instanceof MobEntity) {
                List<Ability> abilityList = EldritchMobsMod.getModifiers((ComponentProvider) livingEntity);
                for (Ability ability : abilityList) {
                    ability.passiveApply((MobEntity) livingEntity);
                }
            }
            return ActionResult.PASS;
        });

        onAbilityUseCallback.ON_ABILITY_USE.register(livingEntity -> {
            if (livingEntity instanceof MobEntity) {
                List<Ability> abilityList = EldritchMobsMod.getModifiers((ComponentProvider) livingEntity);

                for (Ability ability : abilityList) {
                    ability.onAbilityUse((MobEntity) livingEntity);
                }
            }
            return ActionResult.PASS;
        });

        onDeathCallback.ON_DEATH.register((livingEntity, damageSource) -> {
            if (livingEntity instanceof MobEntity) {
                List<Ability> abilityList = EldritchMobsMod.getModifiers((ComponentProvider) livingEntity);

                for (Ability ability : abilityList) {
                    ability.onDeath(livingEntity, damageSource);
                }
            }
            return ActionResult.PASS;
        });
    }
}
