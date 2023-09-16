package net.hyper_pigeon.eldritch_mobs.ability.data;

import com.mojang.brigadier.ParseResults;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.ability.ActivationType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Objects;

public class CustomAbility implements Ability {

    String name;
    AbilityType abilityType;
    AbilitySubType abilitySubType;
    ActivationType activationType;
    String command;

    private long cooldown;
    private long nextUseTime = 0;

    public CustomAbility(String name, AbilityType abilityType, AbilitySubType abilitySubType, ActivationType activationType, String command) {
        this.name = name;
        this.abilityType = abilityType;
        this.abilitySubType = abilitySubType;
        this.activationType = activationType;
        this.command = command;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbilityType getAbilityType() {
        return abilityType;
    }

    @Override
    public AbilitySubType getAbilitySubType() {
        return abilitySubType;
    }

    @Override
    public boolean canUseAbility(MobEntity mobEntity) {
        return mobEntity.world.getTime() > nextUseTime && mobEntity.getTarget() != null;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public void onAbilityUse(MobEntity mobEntity) {
        if (activationType == ActivationType.hasTarget && canUseAbility(mobEntity)) {
            String parsedCommand = this.command.replaceAll("\\{target\\}", mobEntity.getTarget().getUuidAsString());
            parsedCommand = parsedCommand.replaceAll("\\{self\\}", mobEntity.getUuidAsString());
            ServerCommandSource commandSource = mobEntity.getServer().getCommandSource();
            ParseResults<ServerCommandSource> parseResults = mobEntity.getServer().getCommandManager().getDispatcher().parse(parsedCommand, commandSource);
            mobEntity.getServer().getCommandManager().execute(parseResults, parsedCommand);
            nextUseTime = mobEntity.getEntityWorld().getTime() + getCooldown();
        }
    }

    @Override
    public void onDamaged(LivingEntity entity, DamageSource source, float amount) {
        if (activationType == ActivationType.onDamage) {
            String parsedCommand = this.command.replaceAll("\\{self\\}", entity.getUuidAsString());
            MinecraftServer minecraftServer = entity.getServer();
            ServerCommandSource commandSource = minecraftServer.getCommandSource();
            CommandManager commandManager = minecraftServer.getCommandManager();
            if (source.getAttacker() != null && source.getAttacker() instanceof LivingEntity)
                parsedCommand = parsedCommand.replaceAll("\\{attacker\\}", source.getAttacker().getUuidAsString());
            ParseResults<ServerCommandSource> parseResults = commandManager.getDispatcher().parse(parsedCommand, commandSource);
            Objects.requireNonNull(entity.getServer()).getCommandManager().execute(parseResults, parsedCommand);
        }
    }

    @Override
    public void onDamageToTarget(LivingEntity attacker, LivingEntity target, DamageSource source, float amount) {
        if (activationType == ActivationType.onDamageToTarget) {
            String parsedCommand = this.command.replaceAll("\\{self\\}", attacker.getUuidAsString());
            parsedCommand = parsedCommand.replaceAll("\\{target\\}", target.getUuidAsString());

            MinecraftServer minecraftServer = attacker.getServer();
            ServerCommandSource commandSource = minecraftServer.getCommandSource();
            CommandManager commandManager = minecraftServer.getCommandManager();
            ParseResults<ServerCommandSource> parseResults = commandManager.getDispatcher().parse(parsedCommand, commandSource);
            Objects.requireNonNull(attacker.getServer()).getCommandManager().execute(parseResults, parsedCommand);
        }
    }

    @Override
    public void onAttack(LivingEntity attacker, LivingEntity target) {
        if (activationType == ActivationType.onAttack) {
            String parsedCommand = this.command.replaceAll("\\{target\\}", target.getUuidAsString());
            parsedCommand = parsedCommand.replaceAll("\\{self\\}", attacker.getUuidAsString());

            MinecraftServer minecraftServer = target.getServer();
            ServerCommandSource commandSource = minecraftServer.getCommandSource();
            CommandManager commandManager = minecraftServer.getCommandManager();
            ParseResults<ServerCommandSource> parseResults = commandManager.getDispatcher().parse(parsedCommand, commandSource);

            Objects.requireNonNull(target.getServer()).getCommandManager().execute(parseResults, parsedCommand);
        }
    }

    @Override
    public void onDeath(LivingEntity livingEntity, DamageSource damageSource) {
        if (activationType == ActivationType.onDeath) {
            String parsedCommand = this.command.replaceAll("\\{self\\}", livingEntity.getUuidAsString());
            if (damageSource.getAttacker() != null)
                parsedCommand = parsedCommand.replaceAll("\\{killer\\}", damageSource.getAttacker().getUuidAsString());

            MinecraftServer minecraftServer = livingEntity.getServer();
            ServerCommandSource commandSource = minecraftServer.getCommandSource();
            CommandManager commandManager = minecraftServer.getCommandManager();
            ParseResults<ServerCommandSource> parseResults = commandManager.getDispatcher().parse(parsedCommand, commandSource);

            Objects.requireNonNull(livingEntity.getServer()).getCommandManager().execute(parseResults, parsedCommand);
        }
    }

    @Override
    public void passiveApply(MobEntity mobEntity) {
        if (activationType == ActivationType.tick) {
            String parsedCommand = this.command.replaceAll("\\{self\\}", mobEntity.getUuidAsString());

            MinecraftServer minecraftServer = mobEntity.getServer();
            ServerCommandSource commandSource = minecraftServer.getCommandSource();
            CommandManager commandManager = minecraftServer.getCommandManager();
            ParseResults<ServerCommandSource> parseResults = commandManager.getDispatcher().parse(parsedCommand, commandSource);

            Objects.requireNonNull(mobEntity.getServer()).getCommandManager().execute(parseResults, parsedCommand);
        }
    }
}
