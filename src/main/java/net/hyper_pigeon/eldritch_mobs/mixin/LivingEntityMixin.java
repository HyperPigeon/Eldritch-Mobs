package net.hyper_pigeon.eldritch_mobs.mixin;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.callback.*;
import net.hyper_pigeon.eldritch_mobs.rank.MobRank;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobsLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ComponentProvider {


    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow protected abstract net.minecraft.loot.context.LootContext.Builder getLootContextBuilder(boolean causedByPlayer, DamageSource source);

    @Shadow public abstract float getHealth();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private boolean notNormal(ComponentProvider componentProvider){
        if(componentProvider instanceof MobEntity) {
            return EldritchMobsMod.getRank(componentProvider) != MobRank.NONE && EldritchMobsMod.getRank(componentProvider) != MobRank.UNDECIDED;
        }
        return false;
    }



    @Inject(method = "onAttacking", at = @At(value = "FIELD", target = "net/minecraft/entity/LivingEntity.attacking : Lnet/minecraft/entity/LivingEntity;",opcode = Opcodes.PUTFIELD,ordinal = 0, shift = At.Shift.AFTER), cancellable = true)
    private void applyOnAttackAbilities(Entity target, CallbackInfo ci){
        if(target instanceof LivingEntity && this.getType() != EntityType.PLAYER
        && notNormal(this)) {
            ActionResult result = onAttackCallback.ON_ATTACK.invoker().onAttack((LivingEntity) (Object)this,(LivingEntity) target);
            if(result == ActionResult.FAIL) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "damage", at = @At("TAIL"))
    private void applyOnDamageAbilities(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if(this.getType() != EntityType.PLAYER && notNormal(this)){
            ActionResult result = onDamagedCallback.ON_DAMAGED.invoker().onDamaged((LivingEntity) (Object)(this), source, amount);

            if(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOnGlowingMobs && !hasStatusEffect(StatusEffects.GLOWING)) {
                addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2400));
            }

            if(result == ActionResult.FAIL) {
                cir.cancel();
            }

        }
    }

    @Inject(method="damage", at = @At("TAIL"))
    private void applyOnDamageToTarget(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if(source.getAttacker() != null && source.getAttacker() instanceof LivingEntity &&
                !(source.getAttacker() instanceof PlayerEntity) && notNormal((ComponentProvider) source.getAttacker())) {
            ActionResult result = onDamageToTargetCallback.ON_DAMAGE_TO_TARGET.invoker().onDamageToTarget((LivingEntity) source.getAttacker(),
                    (LivingEntity)(Object)this, source, amount);
            if(result == ActionResult.FAIL) {
                cir.cancel();
            }
        }
    }

    @Inject(method="tick", at=@At("TAIL"), cancellable = true)
    private void applyPassiveAbilities(CallbackInfo ci){
        if(this.getType() != EntityType.PLAYER && notNormal(this)){
            ActionResult result = passiveApplyCallback.PASSIVE_APPLY.invoker().passiveApply((LivingEntity) (Object)(this));
            if(result == ActionResult.FAIL) {
                ci.cancel();
            }
        }
    }


    @Inject(method="tick", at=@At("TAIL"), cancellable = true)
    private void useActiveAbilities(CallbackInfo ci){
        if(this.getType() != EntityType.PLAYER && notNormal(this)){
            ActionResult result = onAbilityUseCallback.ON_ABILITY_USE.invoker().onAbilityUse((LivingEntity)(Object)this);
            if(result == ActionResult.FAIL) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "onDeath", at=@At(value = "INVOKE_ASSIGN", target = "net/minecraft/entity/damage/DamageSource.getAttacker ()Lnet/minecraft/entity/Entity;", opcode = Opcodes.INVOKEVIRTUAL,ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void onDeathAbilities(DamageSource damageSource,CallbackInfo  ci){
        if(this.getType() != EntityType.PLAYER && notNormal(this)){
            ActionResult result = onDeathCallback.ON_DEATH.invoker().onDeath((LivingEntity)(Object)(this), damageSource);
            if(result == ActionResult.FAIL) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "dropLoot")
    protected void dropBonusLoot (DamageSource source, boolean causedByPlayer, CallbackInfo info) {
        if(this.getType() != EntityType.PLAYER && notNormal(this)
                && (causedByPlayer || !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.onlyDropLootIfKilledByPlayers)){
            if(EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank() == MobRank.ELITE){
                LootTable lootTable = this.world.getServer().getLootManager().getTable(EldritchMobsLootTables.EliteLootID);
                net.minecraft.loot.context.LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
            }
            else if(EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank() == MobRank.ULTRA){
                LootTable lootTable = this.world.getServer().getLootManager().getTable(EldritchMobsLootTables.UltraLootID);
                net.minecraft.loot.context.LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
                if(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.combinedLootDrop){
                    LootTable eliteLootTable = this.world.getServer().getLootManager().getTable(EldritchMobsLootTables.EliteLootID);
                    eliteLootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
                }
            }
            else if(EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank() == MobRank.ELDRITCH){
                LootTable lootTable = this.world.getServer().getLootManager().getTable(EldritchMobsLootTables.EldritchLootID);
                net.minecraft.loot.context.LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
                if(EldritchMobsMod.ELDRITCH_MOBS_CONFIG.combinedLootDrop){
                    LootTable eliteLootTable = this.world.getServer().getLootManager().getTable(EldritchMobsLootTables.EliteLootID);
                    eliteLootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);

                    LootTable ultraLootTable = this.world.getServer().getLootManager().getTable(EldritchMobsLootTables.UltraLootID);
                    ultraLootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
                }
            }
        }
    }
}
