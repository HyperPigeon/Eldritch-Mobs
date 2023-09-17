package net.hyper_pigeon.eldritch_mobs.ability.passive.defensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class ThornyAbility implements Ability {

    private final EldritchMobsConfig.ThornyConfig thornyConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.thornyConfig;

    @Override
    public String getName() {
        return thornyConfig.name;
    }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.PASSIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.DEFENSIVE;
    }

    @Override
    public void onDamaged(LivingEntity entity, DamageSource source, float amount) {
        if (source.getAttacker() != null && source.getAttacker().isAlive()) {
            source.getAttacker().damage(entity.getDamageSources().magic(), (float) (amount * thornyConfig.thornyReturnDamage));
        }
    }
}
