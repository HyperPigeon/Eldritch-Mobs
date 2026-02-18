package net.hyper_pigeon.eldritch_mobs.mixin;

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
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.ComponentProvider;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ComponentProvider {


    @Unique
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private boolean notNormal(ComponentProvider componentProvider) {
        if (componentProvider instanceof MobEntity) {
            return EldritchMobsMod.getRank(componentProvider) != MobRank.NONE && EldritchMobsMod.getRank(componentProvider) != MobRank.UNDECIDED;
        }
        return false;
    }


    @Inject(method = "onAttacking", at = @At(value = "FIELD", target = "net/minecraft/entity/LivingEntity.attacking : Lnet/minecraft/entity/LivingEntity;", opcode = Opcodes.PUTFIELD, ordinal = 0, shift = At.Shift.AFTER), cancellable = true)
    private void applyOnAttackAbilities(Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity && this.getType() != EntityType.PLAYER
                && notNormal(this)) {
            ActionResult result = onAttackCallback.ON_ATTACK.invoker().onAttack((LivingEntity) (Object) this, (LivingEntity) target);
            if (result == ActionResult.FAIL) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "damage", at = @At("TAIL"), cancellable = true)
    private void applyOnDamageAbilities(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.getType() != EntityType.PLAYER && notNormal(this)) {
            ActionResult result = onDamagedCallback.ON_DAMAGED.invoker().onDamaged((LivingEntity) (Object) (this), source, amount);

            if (EldritchMobsMod.ELDRITCH_MOBS_CONFIG.turnOnGlowingMobs && !hasStatusEffect(StatusEffects.GLOWING)) {
                addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2400));
            }

            if (result == ActionResult.FAIL) {
                cir.cancel();
            }

        }
    }

    @Inject(method = "damage", at = @At("TAIL"), cancellable = true)
    private void applyOnDamageToTarget(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity attacker = source.getAttacker();
        if (
                attacker instanceof LivingEntity
                        && !(attacker instanceof ServerPlayerEntity)
                        && notNormal(attacker.asComponentProvider())
        ) {
            ActionResult result = onDamageToTargetCallback.ON_DAMAGE_TO_TARGET.invoker()
                    .onDamageToTarget((LivingEntity) attacker, (LivingEntity) (Object) this, source, amount);
            if (result == ActionResult.FAIL) cir.cancel();
        }
    }

    @Inject(method = "tick", at = @At("TAIL"), cancellable = true)
    private void applyPassiveAbilities(CallbackInfo ci) {
        if (this.getType() != EntityType.PLAYER && notNormal(this)) {
            ActionResult result = passiveApplyCallback.PASSIVE_APPLY.invoker().passiveApply((LivingEntity) (Object) (this));
            if (result == ActionResult.FAIL) {
                ci.cancel();
            }
        }
    }


    @Inject(method = "tick", at = @At("TAIL"), cancellable = true)
    private void useActiveAbilities(CallbackInfo ci) {
        if (this.getType() != EntityType.PLAYER && notNormal(this)) {
            ActionResult result = onAbilityUseCallback.ON_ABILITY_USE.invoker().onAbilityUse((LivingEntity) (Object) this);
            if (result == ActionResult.FAIL) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "onDeath", at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/entity/damage/DamageSource.getAttacker ()Lnet/minecraft/entity/Entity;", opcode = Opcodes.INVOKEVIRTUAL, ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void onDeathAbilities(DamageSource damageSource, CallbackInfo ci) {
        if (this.getType() != EntityType.PLAYER && notNormal(this)) {
            ActionResult result = onDeathCallback.ON_DEATH.invoker().onDeath((LivingEntity) (Object) (this), damageSource);
            if (result == ActionResult.FAIL) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "dropLoot")
    protected void dropBonusLoot(ServerWorld world, DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
        if (this.getType() != EntityType.PLAYER && notNormal(this)
                && (causedByPlayer || !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.onlyDropLootIfKilledByPlayers)
                && !EldritchMobsMod.ELDRITCH_MOBS_CONFIG.disableLootDrops) {

            MinecraftServer server = this.getEntityWorld().getServer();

            if (server != null) {
                LootWorldContext.Builder builder = new LootWorldContext.Builder(
                        (ServerWorld)this.getWorld()
                )
                        .add(LootContextParameters.THIS_ENTITY, this)
                        .add(LootContextParameters.ORIGIN, this.getPos())
                        .add(LootContextParameters.DAMAGE_SOURCE, damageSource)
                        .addOptional(LootContextParameters.ATTACKING_ENTITY, damageSource.getAttacker())
                        .addOptional(LootContextParameters.DIRECT_ATTACKING_ENTITY, damageSource.getSource());

                LootWorldContext lootContextParameterSet = builder.build(LootContextTypes.ENTITY);

                LootTable eliteLootTable = this.getWorld().getServer().getReloadableRegistries().getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, EldritchMobsLootTables.ELITE_LOOT_ID));
                LootTable ultraLootTable = this.getWorld().getServer().getReloadableRegistries().getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, EldritchMobsLootTables.ULTRA_LOOT_ID));
                LootTable eldritchLootTable = this.getWorld().getServer().getReloadableRegistries().getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, EldritchMobsLootTables.ELDRITCH_LOOT_ID));

                switch (EldritchMobsMod.ELDRITCH_MODIFIERS.get(this).getRank()) {
                    case ELITE -> {
                        LootTable lootTable = this.getWorld().getServer().getReloadableRegistries().getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, EldritchMobsLootTables.ELITE_LOOT_ID));
                        lootTable.generateLoot(lootContextParameterSet, stack -> this.dropStack((ServerWorld)this.getWorld(), stack));
                    }
                    case ULTRA -> {
                        LootTable lootTable = this.getWorld().getServer().getReloadableRegistries().getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, EldritchMobsLootTables.ULTRA_LOOT_ID));
                        lootTable.generateLoot(lootContextParameterSet, stack -> this.dropStack((ServerWorld)this.getWorld(), stack));
                        if (EldritchMobsMod.ELDRITCH_MOBS_CONFIG.combinedLootDrop) {
                            eliteLootTable.generateLoot(lootContextParameterSet, stack -> this.dropStack((ServerWorld)this.getWorld(), stack));
                        }
                    }
                    case ELDRITCH -> {
                        eldritchLootTable.generateLoot(lootContextParameterSet, stack -> this.dropStack((ServerWorld)this.getWorld(), stack));
                        if (EldritchMobsMod.ELDRITCH_MOBS_CONFIG.combinedLootDrop) {
                            eliteLootTable.generateLoot(lootContextParameterSet, stack -> this.dropStack((ServerWorld)this.getWorld(), stack));
                            ultraLootTable.generateLoot(lootContextParameterSet, stack -> this.dropStack((ServerWorld)this.getWorld(), stack));
                        }
                    }
                    default -> {}
                }
            }
        }
    }
}
