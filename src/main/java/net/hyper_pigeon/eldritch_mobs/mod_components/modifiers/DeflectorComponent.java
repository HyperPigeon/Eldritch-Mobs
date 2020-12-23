package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;

public class DeflectorComponent implements ModifierInterface {
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

    @Override
    public void damageActivatedMod(LivingEntity entity, DamageSource source, float amount) {
//        if (amount > 0.0F) {
//            if(source instanceof ProjectileDamageSource) {
//                ProjectileEntity projectileEntity = (ProjectileEntity) source.getSource();
//                if(projectileEntity.getOwner() != null) {
//                    Entity owner = projectileEntity.getOwner();
//                    double targetX = owner.getX();
//                    double targetY = owner.getY();
//                    double targetZ = owner.getZ();
//
//                    double entityX = entity.getX();
//                    double entityY = entity.getY();
//                    double entityZ =  entity.getZ();
//
//                    double diffX = entityX - targetX;
//                    double diffY = entityY - targetY;
//                    double diffZ = entityZ - targetZ;
//
//                    projectileEntity.setOwner(entity);
//                    projectileEntity.addVelocity(diffX,diffY,diffZ);
//                }
//                else {
//                    projectileEntity.setOwner(entity);
//                    projectileEntity.setVelocity(10, projectileEntity.getVelocity().getY(), 10);
//                }
//
//            }
//        }
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
