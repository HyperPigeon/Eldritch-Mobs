package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Vec3d;

public class GhastlyComponent implements ModifierInterface {

    private final static long cooldown = 700;
    private long nextAbilityUse = 0L;

    @Override
    public void useAbility(MobEntity entity) {
        if(entity.getTarget() != null && entity.canSee(entity.getTarget()) && entity.getTarget().isAlive()) {
            long time = entity.getEntityWorld().getTime();
            if (time > nextAbilityUse) {
                nextAbilityUse = time + cooldown;
                LivingEntity target = entity.getTarget();
                double e = 4.0D;
                Vec3d vec3d = entity.getRotationVec(1.0F);
                double f = target.getX() - (entity.getX() + vec3d.x * 4.0D);
                double g = target.getBodyY(0.5D) - (0.5D + entity.getBodyY(0.5D));
                double h = target.getZ() - (entity.getZ() + vec3d.z * 4.0D);
                if (!entity.isSilent()) {
                    entity.getEntityWorld().syncWorldEvent((PlayerEntity)null, 1016, entity.getBlockPos(), 0);
                }

                FireballEntity fireballEntity = new FireballEntity(entity.getEntityWorld(), entity, f, g, h);

                if(EldritchMobsMod.CONFIG.intensity <= 1){
                    fireballEntity.explosionPower = 1;
                }
                else if(EldritchMobsMod.CONFIG.intensity == 2){
                    fireballEntity.explosionPower = 2;
                }
                else {
                    fireballEntity.explosionPower = 3;
                }

                fireballEntity.updatePosition(entity.getX() + vec3d.x * 4.0D, entity.getBodyY(0.5D) + 0.5D, fireballEntity.getZ() + vec3d.z * 4.0D);
                entity.getEntityWorld().spawnEntity(fireballEntity);
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
    public void readFromNbt(CompoundTag compoundTag) {

    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {

    }
}
