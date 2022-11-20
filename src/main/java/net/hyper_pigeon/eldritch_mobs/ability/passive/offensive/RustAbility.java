package net.hyper_pigeon.eldritch_mobs.ability.passive.offensive;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class RustAbility implements Ability {

    private static final EldritchMobsConfig.RustConfig RUST_CONFIG = EldritchMobsMod.ELDRITCH_MOBS_CONFIG.rustConfig;

    @Override
    public String getName() {
        return RUST_CONFIG.name;
    }

    @Override
    public boolean getDisabled() { return RUST_CONFIG.disabled; }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.PASSIVE;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return AbilitySubType.OFFENSIVE;
    }

    @Override
    public void onAttack(LivingEntity attacker, LivingEntity entity) {
        if (entity.isAlive()) {
            if (entity.hasStackEquipped(EquipmentSlot.HEAD)) {
                entity.getEquippedStack(EquipmentSlot.HEAD).damage(RUST_CONFIG.equipmentDamage, entity, p -> p.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
            }
            if (entity.hasStackEquipped(EquipmentSlot.CHEST)) {
                entity.getEquippedStack(EquipmentSlot.CHEST).damage(RUST_CONFIG.equipmentDamage, entity, p -> p.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
            }
            if (entity.hasStackEquipped(EquipmentSlot.LEGS)) {
                entity.getEquippedStack(EquipmentSlot.LEGS).damage(RUST_CONFIG.equipmentDamage, entity, p -> p.sendEquipmentBreakStatus(EquipmentSlot.LEGS));
            }
            if (entity.hasStackEquipped(EquipmentSlot.FEET)) {
                entity.getEquippedStack(EquipmentSlot.FEET).damage(RUST_CONFIG.equipmentDamage, entity, p -> p.sendEquipmentBreakStatus(EquipmentSlot.FEET));
            }
        }
    }
}
