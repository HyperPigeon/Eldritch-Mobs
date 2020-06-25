package net.hyper_pigeon.eldritch_mobs.mixin;


import nerdhub.cardinal.components.api.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity implements ComponentProvider {

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public native Iterable<ItemStack> getArmorItems();

    @Shadow
    public native ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public native void equipStack(EquipmentSlot slot, ItemStack stack);

    @Shadow
    public native Arm getMainArm();

    @Shadow protected int experiencePoints;

    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V")
    private void constructor(EntityType<? extends LivingEntity> entityType, World world, CallbackInfo ci){
        if(!EldritchMobsMod.get_mod_list(this).equals("")){
            //System.out.println(EldritchMobsMod.get_mod_list(this));
            this.setCustomName(new TranslatableText(EldritchMobsMod.get_mod_list(this), new Object[0]));
            this.setCustomNameVisible(true);
        }

    }


    @Inject(at = @At("HEAD"), method = "tick")
    public void ability_try(CallbackInfo callback) {
        if (this.getType() != EntityType.PLAYER && (EldritchMobsMod.isElite(this)
            ||EldritchMobsMod.isUltra(this)||EldritchMobsMod.isEldritch(this))) {
            if(!this.hasStatusEffect(StatusEffects.HEALTH_BOOST)){
                if(EldritchMobsMod.isEldritch(this)){
                    int level = (int) ((this.getMaxHealth()*12)/2);
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 1000000000,level));
                    this.heal(level*2);
                }
                else if(EldritchMobsMod.isUltra(this)){
                    int level = (int) ((this.getMaxHealth()*8)/2);
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 1000000000,level));
                    this.heal(level*2);
                }
                else if(EldritchMobsMod.isElite(this)){
                    int level = (int) ((this.getMaxHealth()*4)/2);
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 1000000000,level));
                    this.heal(level*2);
                }
            }
            EldritchMobsMod.useAbility(this);
        }
    }

    @Inject(at = @At("HEAD"), method = "getCurrentExperience", cancellable = true)
    protected void getCurrentExperience(PlayerEntity player, CallbackInfoReturnable<Integer> callback) {
        if(EldritchMobsMod.isElite(this)){
            if(EldritchMobsMod.isUltra(this)){
                if(EldritchMobsMod.isEldritch(this)){
                    this.experiencePoints = (int)((float)this.experiencePoints * 48.0F);
                }else{
                    this.experiencePoints = (int)((float)this.experiencePoints * 32.0F);
                }
            }
            else {
                this.experiencePoints = (int)((float)this.experiencePoints * 16.0F);
            }
        }
    }






}
