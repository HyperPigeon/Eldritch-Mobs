package net.hyper_pigeon.eldritch_mobs.mixin;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.item.SoothingLantern;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.hyper_pigeon.eldritch_mobs.persistent_state.SoothingLanternPersistentState;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ComponentProvider {

    @Shadow
    public native boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow
    public native boolean teleport(double x, double y, double z, boolean particleEffects);

    @Shadow
    public native void sendEquipmentBreakStatus(EquipmentSlot slot);

    @Shadow
    public abstract LivingEntity getAttacker();

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow protected abstract void applyDamage(DamageSource source, float amount);

    @Shadow public native void heal(float amount);

    @Shadow protected abstract net.minecraft.loot.context.LootContext.Builder
    getLootContextBuilder(boolean causedByPlayer, DamageSource source);

    @Shadow public abstract boolean removeStatusEffect(StatusEffect type);

    @Shadow public abstract boolean clearStatusEffects();

    @Unique
    private boolean eldritchMobs_hasConfiguredName = false;

    @Unique
    private boolean checkedIfInLampChunk;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void configureCustomName(CallbackInfo ci) {

        // only attempt to apply a custom name to the mob on their first tick
        if(!world.isClient && !eldritchMobs_hasConfiguredName
        && (this.getType().isIn(EldritchMobsMod.ELDRITCH_ALLOWED)) &&
                !(this.getType().isIn(EldritchMobsMod.ELDRITCH_BLACKLIST))) {
            ModifierInterface modifiers = EldritchMobsMod.ELDRITCH_MODIFIERS.get(this);

            // only apply custom name if the mob doesn't have one and their modifier provides one
            boolean hasCustomName = this.getCustomName() != null && this.getCustomName().asString().equals("");
            if (!modifiers.get_mod_string().equals("") && !hasCustomName) {
                this.setCustomNameVisible(false);
                if (!EldritchMobsMod.CONFIG.turnOffNames) {
                    this.setCustomName(new TranslatableText(modifiers.get_mod_string(), new Object[0]));
                }
            }

            eldritchMobs_hasConfiguredName = true;
        }
    }

   @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void removeModifiersIfInLampChunk(CallbackInfo callbackInfo){
        //only attempt to remove modifiers and status effects from a mob on their first tick
        if(!world.isClient && !checkedIfInLampChunk &&
                (this.getType().isIn(EldritchMobsMod.ELDRITCH_ALLOWED)) &&
                !(this.getType().isIn(EldritchMobsMod.ELDRITCH_BLACKLIST))) {

            ModifierInterface modifiers = EldritchMobsMod.ELDRITCH_MODIFIERS.get(this);
            if(SoothingLanternPersistentState.get
                            ((ServerWorld) this.getEntityWorld()).
                            containsChunk(new ChunkPos(this.getBlockPos()))){
                modifiers.spawnedInLampChunk();
                this.clearStatusEffects();
                this.setCustomName(null);
            }
            checkedIfInLampChunk = true;
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCheckedIfInLampChunkToTag(NbtCompound compoundTag, CallbackInfo callbackInfo){
        compoundTag.putBoolean("checkedIfInLampChunk", checkedIfInLampChunk);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCheckedIfInLampChunkToTag(NbtCompound tag, CallbackInfo callbackInfo){
        checkedIfInLampChunk = tag.getBoolean("checkedIfInLampChunk");
    }

//    @Inject(
//            method = "tick",
//            at = @At("HEAD")
//    )
//    private void removeSpecialModifiersIfInLampChunk(CallbackInfo ci) {
//        // only attempt to apply a custom name to the mob on their first tick
//        if(!world.isClient && !checkedIfInLampChunk) {
//            ModifierInterface modifiers = EldritchMobsMod.ELDRITCH_MODIFIERS.get(this);
//
//            if(SoothingLanternPersistentState.get((ServerWorld) this.getEntityWorld()).containsChunk(new ChunkPos(this.getBlockPos()))){
//                modifiers.spawnedInLampChunk();
//                this.clearStatusEffects();
//                this.setCustomName(null);
//            }
//
//            checkedIfInLampChunk = true;
//        }
//    }

//    protected void afterSpawn(){
//        if(!world.isClient) {
//            System.out.println("Check1");
//            ModifierInterface modifiers = EldritchMobsMod.ELDRITCH_MODIFIERS.get(this);
//            if(SoothingLanternPersistentState.get((ServerWorld) this.getEntityWorld()).containsChunk(new ChunkPos(this.getBlockPos()))){
//                System.out.println("Check2");
//                modifiers.spawnedInLampChunk();
//                this.clearStatusEffects();
//                //this.setCustomName(null);
//            }
//        }
//    }

    private boolean teleportTo(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

        while(mutable.getY() > 0 && !this.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockState = this.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (bl && !bl2) {
            boolean bl3 = this.teleport(x, y, z, false);
            if (bl3 && !this.isSilent()) {
                this.world.playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return bl3;
        } else {
            return false;
        }
    }

    protected boolean teleportRandomly() {
        if (!this.world.isClient() && this.isAlive()) {
            double d = this.getX() + (this.random.nextDouble() - 0.5D) * 25.0D;
            double e = this.getY() + (double)(this.random.nextInt(64) - 32);
            double f = this.getZ() + (this.random.nextDouble() - 0.5D) * 25.0D;
            return this.teleportTo(d, e, f);
        } else {
            return false;
        }
    }

    @Inject(at = @At("HEAD"), method = "onAttacking")
    public void onAttackMods(Entity target, CallbackInfo info){
        if(this.getType() != EntityType.PLAYER){
            if(target instanceof LivingEntity){
                if(EldritchMobsMod.hasMod(this, "lifesteal")) {
                    LivingEntity livingTarget = (LivingEntity) target;

                    if(EldritchMobsMod.CONFIG.intensity <= 1){
                        this.heal((livingTarget.getMaxHealth() - livingTarget.getHealth())/3);
                    }
                    else if(EldritchMobsMod.CONFIG.intensity == 2){
                        this.heal((livingTarget.getMaxHealth() - livingTarget.getHealth())/2);
                    }
                    else {
                        this.heal(livingTarget.getMaxHealth() - livingTarget.getHealth());
                    }

                }
                if(EldritchMobsMod.hasMod(this, "rust")) {
                    Random r = new Random();
                    int randomInt_helmet = r.nextInt(100) + 1;
                    int randomInt_chest = r.nextInt(100) + 1;
                    int randomInt_leggings = r.nextInt(100) + 1;
                    int randomInt_boots = r.nextInt(100) + 1;

                    if(EldritchMobsMod.CONFIG.intensity <= 1) {
                        if (randomInt_helmet <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
                            this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                        if (randomInt_chest <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.CHEST);
                            this.equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
                        }
                        if (randomInt_leggings <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.LEGS);
                            this.equipStack(EquipmentSlot.LEGS, ItemStack.EMPTY);
                        }
                        if (randomInt_boots <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.FEET);
                            this.equipStack(EquipmentSlot.FEET, ItemStack.EMPTY);
                        }
                    }
                    else if(EldritchMobsMod.CONFIG.intensity == 2) {
                        if (randomInt_helmet <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
                            this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                        if (randomInt_chest <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.CHEST);
                            this.equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
                        }
                        if (randomInt_leggings <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.LEGS);
                            this.equipStack(EquipmentSlot.LEGS, ItemStack.EMPTY);
                        }
                        if (randomInt_boots <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.FEET);
                            this.equipStack(EquipmentSlot.FEET, ItemStack.EMPTY);
                        }
                    }
                    else {
                        if (randomInt_helmet <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
                            this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                        if (randomInt_chest <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.CHEST);
                            this.equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
                        }
                        if (randomInt_leggings <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.LEGS);
                            this.equipStack(EquipmentSlot.LEGS, ItemStack.EMPTY);
                        }
                        if (randomInt_boots <= 10) {
                            this.sendEquipmentBreakStatus(EquipmentSlot.FEET);
                            this.equipStack(EquipmentSlot.FEET, ItemStack.EMPTY);
                        }
                    }


                }
                if(EldritchMobsMod.hasMod(this, "yeeter")){
                    LivingEntity livingTarget = (LivingEntity) target;

                    if(EldritchMobsMod.CONFIG.intensity <= 1){
                        livingTarget.addVelocity(0, 0.33, 0);
                    }
                    else if(EldritchMobsMod.CONFIG.intensity == 2){
                        livingTarget.addVelocity(0, 0.66, 0);
                    }
                    else {
                        livingTarget.addVelocity(0, 1.0, 0);
                    }
                }
            }
        }
    }



    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void applyModifiers(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
        if(this.isAlive() && this != null && (this.getType() != EntityType.PLAYER)) {
            Entity attacker = source.getAttacker();
            LivingEntity the_attacker = this.getAttacker();
            if(attacker != null) {
                if((EldritchMobsMod.isElite(this)
                        ||EldritchMobsMod.isUltra(this)||EldritchMobsMod.isEldritch(this))) {
//                    if(!this.isCustomNameVisible()) {
//                        this.setCustomNameVisible(true);
//                    }
                    if(EldritchMobsMod.CONFIG.turnOnGlowingMobs){
                        this.setGlowing(true);
                    }
                    if (EldritchMobsMod.hasMod(this, "berserk")) {
                        this.applyDamage(source, amount);
                    }
                    if (EldritchMobsMod.hasMod(this, "deflector")) {
                        if (amount >= 0.0F) {
                            if (source instanceof ProjectileDamageSource && (source.getSource() instanceof PersistentProjectileEntity)) {
                                PersistentProjectileEntity projectileEntity = (PersistentProjectileEntity ) source.getSource();

                                if (projectileEntity.getOwner() != null) {
                                    Entity owner = projectileEntity.getOwner();
                                    double targetX = owner.getX();
                                    double targetY = owner.getY();
                                    double targetZ = owner.getZ();

                                    double entityX = this.getX();
                                    double entityY = this.getY();
                                    double entityZ = this.getZ();

                                    double diffX = entityX - targetX;
                                    double diffY = entityY - targetY;
                                    double diffZ = entityZ - targetZ;

                                    //projectileEntity.setOwner(this);
                                    projectileEntity.addVelocity(diffX, diffY, diffZ);
                                } else {
                                    projectileEntity.setOwner(this);
                                    projectileEntity.setVelocity(10, projectileEntity.getVelocity().getY(), 10);
                                }
                                callback.setReturnValue(false);
                                //callback.cancel();
                            }
                        }
                    }
                    if (EldritchMobsMod.hasMod(this, "ender")) {
                        if ((source instanceof ProjectileDamageSource) && (source.getSource() instanceof PersistentProjectileEntity)) {
                            for(int i = 0; i < 32; ++i) {
                                if (this.teleportRandomly()) {
                                    callback.setReturnValue(false);
                                }
                            }
                        }
                    }
                    if (EldritchMobsMod.hasMod(this, "thorny")) {
                        if(EldritchMobsMod.CONFIG.intensity <= 1){
                            attacker.damage(DamageSource.MAGIC, amount/5);
                        }
                        else if(EldritchMobsMod.CONFIG.intensity == 2){
                            attacker.damage(DamageSource.MAGIC, amount/4);
                        }
                        else {
                            attacker.damage(DamageSource.MAGIC, amount/3);
                        }

                    }
                    if (EldritchMobsMod.hasMod(this, "toxic") && the_attacker != null) {
                        if(EldritchMobsMod.CONFIG.intensity <= 1){
                            the_attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 250, 0));
                        }
                        else if(EldritchMobsMod.CONFIG.intensity == 2){
                            the_attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 0));
                        }
                        else {
                            the_attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 350, 0));
                        }
                    }
                    if (EldritchMobsMod.hasMod(this, "withering") && the_attacker != null) {
                        the_attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 250, 0));
                    }

                    EldritchMobsMod.use_damageActivatedAbility(this, source, amount);

                }
            }

        }
    }

    @Inject(at = @At("HEAD"), method = "dropLoot")
    protected void dropEldritchLoot (DamageSource source, boolean causedByPlayer, CallbackInfo info) {
        if(!EldritchMobsMod.CONFIG.enableCustomLoot) {
            if (EldritchMobsMod.isElite(this) && causedByPlayer) {
                Identifier identifier = new Identifier("eldritch_mobs:entity/elite_loot");
                LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
                net.minecraft.loot.context.LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
            }
            if (EldritchMobsMod.isUltra(this) && causedByPlayer) {
                Identifier identifier = LootTables.SHIPWRECK_TREASURE_CHEST;
                LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
                net.minecraft.loot.context.LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
            }
            if (EldritchMobsMod.isEldritch(this) && causedByPlayer) {
                Identifier identifier = LootTables.END_CITY_TREASURE_CHEST;
                LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
                net.minecraft.loot.context.LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
            }
        }
        else {
            if (EldritchMobsMod.isElite(this) && causedByPlayer) {
                Identifier identifier = EldritchMobsMod.UserDefinedEliteLootID;
                LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
                net.minecraft.loot.context.LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
            }
            if (EldritchMobsMod.isUltra(this) && causedByPlayer) {
                Identifier identifier = EldritchMobsMod.UserDefinedUltraLootID;
                LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
                net.minecraft.loot.context.LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
            }
            if (EldritchMobsMod.isEldritch(this) && causedByPlayer) {
                Identifier identifier = EldritchMobsMod.UserDefinedEldritchLootID;
                LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
                net.minecraft.loot.context.LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
            }
        }
    }

}
