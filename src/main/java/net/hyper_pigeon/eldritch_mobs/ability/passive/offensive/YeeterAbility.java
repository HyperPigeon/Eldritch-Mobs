package net.hyper_pigeon.eldritch_mobs.ability.passive.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.LivingEntity;

public class YeeterAbility implements Ability {

    private final EldritchMobsConfig.YeeterConfig yeeterConfig = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.yeeterConfig;

    @Override
    public String getName() { return yeeterConfig.name; }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.PASSIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.OFFENSIVE;
    }

    @Override
    public void onAttack(LivingEntity attacker, LivingEntity target) {
        target.addVelocity(0, yeeterConfig.yeetAmount, 0);
    }
}
