package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class SnatcherComponent implements ModifierInterface {

    private final static long cooldown = 450;
    private long nextAbilityUse = 0L;

    @Override
    public void useAbility(MobEntity entity) {
        if(entity.getAttacking() != null && entity.canSee(entity.getTarget())) {
            long time = entity.getEntityWorld().getTime();
            if (time > nextAbilityUse) {
                nextAbilityUse = time + cooldown;
                LivingEntity target = entity.getAttacking();

                if(target instanceof PlayerEntity) {
                    Random random = new Random();
                    int random_int = random.nextInt(5)+1;

                    if (EldritchMobsMod.CONFIG.intensity <= 1) {
                        if(random_int <= 1) {
                            ((PlayerEntity) target).dropSelectedItem(true);
                        }
                    }
                    else if(EldritchMobsMod.CONFIG.intensity == 2){
                        if(random_int <= 2) {
                            ((PlayerEntity) target).dropSelectedItem(true);
                        }
                    }
                    else {
                        if(random_int <= 3) {
                            ((PlayerEntity) target).dropSelectedItem(true);
                        }
                    }
                }

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
    public void fromTag(CompoundTag compoundTag) {

    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        return null;
    }



}
