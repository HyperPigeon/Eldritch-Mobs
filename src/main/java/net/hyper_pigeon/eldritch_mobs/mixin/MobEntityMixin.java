package net.hyper_pigeon.eldritch_mobs.mixin;


import me.shedaniel.autoconfig.AutoConfig;
import nerdhub.cardinal.components.api.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

    private ArrayList<ServerPlayerEntity> playersList= new ArrayList();

    boolean nameSet = false;

    EldritchMobsConfig config = AutoConfig.getConfigHolder(EldritchMobsConfig.class).getConfig();

    private static final Predicate<LivingEntity> PLAYER_ENTITY_FILTER = (livingEntity) -> {
        if (livingEntity == null) {
            return false;
        } else if (livingEntity instanceof PlayerEntity) {
            return true;
        } else {
            return false;
        }
    };

//    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V")
//    private void constructor(EntityType<? extends LivingEntity> entityType, World world, CallbackInfo ci){
//        if(!EldritchMobsMod.get_mod_list(this).equals("")){
//            //System.out.println(EldritchMobsMod.get_mod_list(this));
//            if(!(entityType == EntityType.WITHER) && !config.turnOffNames) {
//                this.setCustomName(new TranslatableText(EldritchMobsMod.get_mod_list(this), new Object[0]));
//            }
//            //this.setCustomNameVisible(true);
//        }
//
//    }

//    private EldritchBossBar eldritchBossBar;
//    private BossBarS2CPacket bossBarS2CPacket = new BossBarS2CPacket();
    private ServerBossBar bossBar;
    //private PlayerEntity currentPlayer;

    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V")
    private void constructor(EntityType<? extends LivingEntity> entityType, World world, CallbackInfo ci){
        if(this.getCustomName() != null) {
            this.bossBar = new ServerBossBar(this.getCustomName(), BossBar.Color.GREEN,
                    BossBar.Style.PROGRESS);
        }
        else {
            this.bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.GREEN,
                    BossBar.Style.PROGRESS);
        }
    }

    @Inject(at = @At("HEAD"), method = "mobTick")
    private void mobTick(CallbackInfo info) {
        if(!nameSet){
            if(this.getCustomName() != null) {
                this.bossBar.setName(this.getCustomName());
            }
            else {
                this.bossBar.setName(this.getDisplayName());
            }
            nameSet = true;
        }

        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }


//    @Override
//    public void onStartedTrackingBy(ServerPlayerEntity player) {
//        super.onStartedTrackingBy(player);
//        if((EldritchMobsMod.isElite(this))) {
//            this.bossBar.addPlayer(player);
//        }
//    }
//
    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }


//    public void onStartedTrackingBy(ServerPlayerEntity player) {
//        super.onStartedTrackingBy(player);
//        if((EldritchMobsMod.isElite(this))) {
//            this.bossBar.addPlayer(player);
//        }
//    }
//
//    @Override
//    public void onStoppedTrackingBy(ServerPlayerEntity player) {
//        super.onStoppedTrackingBy(player);
//        this.bossBar.removePlayer(player);
//    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void addBossBar(CallbackInfo callbackInfo){
        if(!EldritchMobsMod.CONFIG.turnOffBossBars) {
            if (EldritchMobsMod.isElite(this)) {
                Box box_1 = this.getBoundingBox().expand(30.0D);
                List<ServerPlayerEntity> list = this.world.getEntitiesByClass(ServerPlayerEntity.class, box_1,
                        PLAYER_ENTITY_FILTER);
                ArrayList<ServerPlayerEntity> removeList = new ArrayList<>();
                playersList.addAll(list);

                for (ServerPlayerEntity player : playersList) {
                    if (isPlayerStaring(player)) {
                        bossBar.addPlayer(player);
                    } else {
                        if (bossBar.getPlayers().contains(player)) {
                            bossBar.removePlayer(player);
                            removeList.add(player);
                        }
                    }
                }

                playersList.removeAll(removeList);
            }
        }
    }

//    @Inject(at = @At("HEAD"), method = "tickMovement")
//    public void addBossBar(CallbackInfo callbackInfo){
//        Box box_1 = this.getBoundingBox().expand(30.0D);
//        ArrayList<ServerPlayerEntity> playersList_two = new ArrayList<>();
//        List<LivingEntity> list = this.world.getEntities(LivingEntity.class, box_1,
//                LIVING_ENTITY_FILTER);
//        Iterator entityIterator = list.iterator();
//        while(entityIterator.hasNext()) {
//            LivingEntity livingEntity = (LivingEntity) entityIterator.next();
//            if(livingEntity instanceof PlayerEntity){
//                playersList_two.add((ServerPlayerEntity) livingEntity);
//                bossBar.addPlayer((ServerPlayerEntity) livingEntity);
//            }
//        }
//
//        ArrayList<ServerPlayerEntity> remove_boss_bar = new ArrayList<>();
//        remove_boss_bar.addAll(playersList);
//        remove_boss_bar.addAll(playersList_two);
//        ArrayList<ServerPlayerEntity> intersection = playersList;
//        intersection.retainAll(playersList_two);
//        remove_boss_bar.removeAll(intersection);
//
//        for (ServerPlayerEntity player : remove_boss_bar) {
//            bossBar.removePlayer(player);
//        }
//    }

    private boolean isPlayerStaring(PlayerEntity player) {
        Vec3d vec3d = player.getRotationVec(1.0F).normalize();
        Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double d = vec3d2.length();
        vec3d2 = vec3d2.normalize();
        double e = vec3d.dotProduct(vec3d2);
        return e > 1.0D - 0.025D / d ? player.canSee(this) : false;
    }

//    @Override
//    public void onStartedTrackingBy(ServerPlayerEntity player) {
//        super.onStartedTrackingBy(player);
//        System.out.println("check1");
//        //currentPlayer = player;
//        System.out.println((EldritchMobsMod.isElite(this)
//                ||EldritchMobsMod.isUltra(this)||EldritchMobsMod.isEldritch(this)));
//        System.out.println(player.canSee(this));
//        if((EldritchMobsMod.isElite(this)
//                ||EldritchMobsMod.isUltra(this)||EldritchMobsMod.isEldritch(this))&& player.canSee(this)) {
//            System.out.println("check2");
//            this.bossBar.addPlayer(player);
//        }
//    }
//


    @Inject(at = @At("HEAD"), method = "tick")
    public void ability_try(CallbackInfo callback) {
        if (this.getType() != EntityType.PLAYER && (EldritchMobsMod.isElite(this)
            ||EldritchMobsMod.isUltra(this)||EldritchMobsMod.isEldritch(this))) {

            if(EldritchMobsMod.CONFIG.healthIncrease) {
                if (!this.hasStatusEffect(StatusEffects.HEALTH_BOOST)) {
                    if (EldritchMobsMod.isEldritch(this)) {
                        int level = (int) (config.EldritchHealthMod);
                        StatusEffectInstance healthBoostInstance =
                                new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 10000000, level);
                        //healthBoostInstance.isPermanent();
                        this.addStatusEffect(healthBoostInstance);
                        this.heal(this.getMaxHealth() * 10);
                    } else if (EldritchMobsMod.isUltra(this)) {
                        int level = (int) (config.UltraHealthMod);
                        StatusEffectInstance healthBoostInstance =
                                new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 10000000, level);
                        //healthBoostInstance.isPermanent();
                        this.addStatusEffect(healthBoostInstance);
                        this.heal(this.getMaxHealth() * 10);
                    } else if (EldritchMobsMod.isElite(this)) {
                        int level = (int) config.EliteHealthMod;
                        StatusEffectInstance healthBoostInstance =
                                new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 10000000, level);
                        //healthBoostInstance.isPermanent();
                        this.addStatusEffect(healthBoostInstance);
                        this.heal(this.getMaxHealth() * 10);
                    }
                }
            }
            else if(EldritchMobsMod.CONFIG.healthMult){
                if (!this.hasStatusEffect(StatusEffects.HEALTH_BOOST)) {
                    if (EldritchMobsMod.isEldritch(this)) {
                        int level = (int) (config.EldritchHealthMult*(this.getHealth()/4));
                        StatusEffectInstance healthBoostInstance =
                                new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 10000000, level);
                        healthBoostInstance.isPermanent();
                        this.addStatusEffect(healthBoostInstance);
                        this.heal(this.getMaxHealth() * 10);
                    } else if (EldritchMobsMod.isUltra(this)) {
                        int level = (int) (config.UltraHealthMult*(this.getHealth()/4));
                        StatusEffectInstance healthBoostInstance =
                                new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 10000000, level);
                        healthBoostInstance.isPermanent();
                        this.addStatusEffect(healthBoostInstance);
                        this.heal(this.getMaxHealth() * 10);
                    } else if (EldritchMobsMod.isElite(this)) {
                        int level = (int) (config.EliteHealthMult*(this.getHealth()/4));
                        StatusEffectInstance healthBoostInstance =
                                new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 10000000, level);
                        healthBoostInstance.isPermanent();
                        this.addStatusEffect(healthBoostInstance);
                        this.heal(this.getMaxHealth() * 10);
                    }
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
                    this.experiencePoints = (int)((float)this.experiencePoints * EldritchMobsMod.CONFIG.EldritchExpMult);
                }else{
                    this.experiencePoints = (int)((float)this.experiencePoints * EldritchMobsMod.CONFIG.UltraExpMult);
                }
            }
            else {
                this.experiencePoints = (int)((float)this.experiencePoints * EldritchMobsMod.CONFIG.EliteExpMult);
            }
        }
    }






}
