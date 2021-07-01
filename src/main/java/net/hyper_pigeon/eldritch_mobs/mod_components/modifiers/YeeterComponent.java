package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;

public class YeeterComponent implements ModifierInterface {

    private final long blastoff_cooldown = 325;
    private long blastoff_nextAbilityUse = 0L;

    @Override
    public void useAbility(MobEntity entity) {

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

    //fix yeeter

    @Override
    public void damageActivatedMod(LivingEntity entity, DamageSource source, float amount) {
        long time = source.getAttacker().getEntityWorld().getTime();
        if (time > blastoff_nextAbilityUse) {
            if(EldritchMobsMod.CONFIG.intensity <= 1) {
                entity.addVelocity(0, 0.33, 0);
            }
            else if(EldritchMobsMod.CONFIG.intensity == 2){
                entity.addVelocity(0, 0.66, 0);
            }
            else {
                entity.addVelocity(0, 1.0, 0);
            }
            blastoff_nextAbilityUse = time + blastoff_cooldown;
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
    public void spawnedInLampChunk() {

    }

    @Override
    public void readFromNbt(NbtCompound compoundTag) {

    }

    @Override
    public void writeToNbt(NbtCompound compoundTag) {

    }
}
