package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.nbt.CompoundTag;

public class DrowningComponent implements ModifierInterface {
    @Override
    public void useAbility(MobEntity entity) {
        if(entity.getTarget() != null && entity.canSee(entity.getTarget())) {
            LivingEntity target= entity.getTarget();
            if(!(target.hasStatusEffect(StatusEffects.WATER_BREATHING))) {
                target.damage(DamageSource.DROWN, 0.15f);
            }
        }
    }

    @Override
    public void setRank() {

    }

    @Override
    public void setMods() {

    }

    @Override
    public boolean hasMod(String name) {
        return false;
    }

    @Override
    public void damageActivatedMod(LivingEntity entity, DamageSource source, float amount) {
        LivingEntity attacker = entity.getAttacker();
        if(attacker != null) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 225, 0));
        }
    }

    @Override
    public boolean isEldritch() {
        return false;
    }

    @Override
    public String get_mod_string() {
        return null;
    }

    @Override
    public boolean isElite() {
        return false;
    }

    @Override
    public boolean isUltra() {
        return false;
    }

    @Override
    public void setIs_elite(boolean bool) {

    }

    @Override
    public void setIs_ultra(boolean bool) {

    }

    @Override
    public void setIs_eldritch(boolean bool) {

    }

    @Override
    public void fromTag(CompoundTag compoundTag) {

    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        return null;
    }
}
