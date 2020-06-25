package net.hyper_pigeon.eldritch_mobs.mod_components.modifiers;

import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class AlchemyComponent implements ModifierInterface {

    /*public boolean is_elite = false;
    public boolean is_ultra = false;
    public boolean is_eldritch = false;*/

    private final static long alch_cooldown = 150;
    private long alchemy_nextAbilityUse = 0L;

    /*public void setRank() {
        Random random = new Random();
        int random_int_one  = random.nextInt(10)+1;
        if(random_int_one == 1){
            is_elite = true;
            int random_int_two  = random.nextInt(10)+1;
            if(random_int_two <= 2){
                is_ultra = true;
                int random_int_three  = random.nextInt(10)+1;
                if(random_int_three <= 2){
                    is_eldritch = true;
                }
            }
        }

    }*/

    public void useAbility(MobEntity entity) {
        if(entity.getTarget() != null) {
            long time = entity.getEntityWorld().getTime();
            if (time > alchemy_nextAbilityUse) {
                alchemy_nextAbilityUse = time + alch_cooldown;
                LivingEntity target = entity.getTarget();
                Vec3d vec3d = target.getVelocity();
                double d = target.getX() + vec3d.x - entity.getX();
                double e = target.getEyeY() - 1.100000023841858D - entity.getY();
                double f = target.getZ() + vec3d.z - entity.getZ();
                float g = MathHelper.sqrt(d * d + f * f);
                Potion potion = Potions.STRONG_HARMING;
                PotionEntity potionEntity = new PotionEntity(entity.world, entity);
                potionEntity.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
                potionEntity.pitch -= -20.0F;
                potionEntity.setVelocity(d, e + (double) (g * 0.2F), f, 0.75F, 8.0F);

                entity.world.spawnEntity(potionEntity);
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
    public void fromTag(CompoundTag compoundTag) {

    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        return compoundTag;
    }


}
