package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;

import java.util.UUID;

public class DuplicatorComponent implements ModifierInterface {

    private final static long cooldown = 3000;
    private long nextAbilityUse = 0L;

    @Override
    public void useAbility(MobEntity entity) {
        if(entity.getTarget() != null && entity.canSee(entity.getTarget()) && entity.getTarget().isAlive()) {
            //MobEntity copy_ally = entity
            long time = entity.getEntityWorld().getTime();
            if (time > nextAbilityUse) {
                nextAbilityUse = time + cooldown;
                //copy_ally.setUuid(UUID.randomUUID());
                //copy_ally.setPos(entity.getX()+1, entity.getY(), entity.getZ()+2);
                //entity.getEntityWorld().spawnEntity(copy_ally);
                entity.getType().spawn((ServerWorld) entity.getEntityWorld(), null,null,null, entity.getBlockPos(), SpawnReason.REINFORCEMENT, true, true);
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
