package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class StormyComponent implements ModifierInterface {

    private final static long cooldown = 800;
    private long nextAbilityUse = 0L;

    @Override
    public void useAbility(MobEntity entity) {
        if(entity.getTarget() != null) {
            long time = entity.getEntityWorld().getTime();
            if (time > nextAbilityUse) {
                nextAbilityUse = time + cooldown;
                LivingEntity target = entity.getTarget();
                LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, entity.getEntityWorld());

                Random random = new Random();
                double random_int = random.nextInt(3);
                double random_int2 = random.nextInt(3);
                double random_int3 = random.nextInt(3);

                lightningEntity.setPos(target.getX() + random_int, target.getY() + random_int2, target.getZ() + random_int3);
                entity.getEntityWorld().spawnEntity(lightningEntity);
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
