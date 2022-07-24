package net.hyper_pigeon.eldritch_mobs.ability.data;

import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.ability.ActivationType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;

import java.util.Objects;

public class CustomAbility  implements Ability {

    String name;
    AbilityType abilityType;
    AbilitySubType abilitySubType;
    ActivationType activationType;
    String command;

    private long cooldown;
    private long nextUseTime = 0;

    public CustomAbility(String name, AbilityType abilityType, AbilitySubType abilitySubType, ActivationType activationType, String command){
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
    public boolean canUseAbilty(MobEntity mobEntity) {
        if(mobEntity.world.getTime() > nextUseTime && mobEntity.getTarget() != null){
            return true;
        }
        return false;
    }

    public long getCooldown(){
        return cooldown;
    }

    public void setCooldown(long cooldown){
        this.cooldown = cooldown;
    }

    @Override
    public void onAbilityUse(MobEntity mobEntity) {
        if(activationType == ActivationType.hasTarget && canUseAbilty(mobEntity)){
            String parsedCommand = this.command.replaceAll("\\{target\\}",mobEntity.getTarget().getUuidAsString());
            parsedCommand = parsedCommand.replaceAll("\\{self\\}", mobEntity.getUuidAsString());
            mobEntity.getServer().getCommandManager().execute(mobEntity.getServer().getCommandSource(),parsedCommand);
            nextUseTime = mobEntity.getEntityWorld().getTime() + getCooldown();
        }
    }

    @Override
    public void onDamaged(LivingEntity entity, DamageSource source, float amount) {
        if(activationType == ActivationType.onDamage){
            String parsedCommand = this.command.replaceAll("\\{self\\}", entity.getUuidAsString());
            if(source.getAttacker() != null && source.getAttacker() instanceof LivingEntity)
                parsedCommand = this.command.replaceAll("\\{attacker\\}", source.getAttacker().getUuidAsString());
            Objects.requireNonNull(entity.getServer()).getCommandManager().execute(entity.getServer().getCommandSource(),parsedCommand);
        }
    }

    @Override
    public void onDamageToTarget(LivingEntity attacker, LivingEntity target, DamageSource source, float amount) {
        if(activationType == ActivationType.onDamageToTarget){
            String parsedCommand = this.command.replaceAll("\\{self\\}", attacker.getUuidAsString());
            parsedCommand = parsedCommand.replaceAll("\\{target\\}", target.getUuidAsString());
            Objects.requireNonNull(attacker.getServer()).getCommandManager().execute(attacker.getServer().getCommandSource(),parsedCommand);
        }
    }

    @Override
    public void onAttack(LivingEntity attacker, LivingEntity target) {
        if(activationType == ActivationType.onAttack){
            String parsedCommand = this.command.replaceAll("\\{target\\}", target.getUuidAsString());
            parsedCommand = parsedCommand.replaceAll("\\{self\\}", attacker.getUuidAsString());
            Objects.requireNonNull(target.getServer()).getCommandManager().execute(target.getServer().getCommandSource(),parsedCommand);
        }
    }

    @Override
    public void onDeath(LivingEntity livingEntity, DamageSource damageSource) {
        if(activationType == ActivationType.onDeath){
            String parsedCommand = this.command.replaceAll("\\{self\\}", livingEntity.getUuidAsString());
            if(damageSource.getAttacker() != null)
                parsedCommand = parsedCommand.replaceAll("\\{killer\\}", damageSource.getAttacker().getUuidAsString());
            Objects.requireNonNull(livingEntity.getServer()).getCommandManager().execute(livingEntity.getServer().getCommandSource(),parsedCommand);
        }
    }

    @Override
    public void passiveApply(MobEntity mobEntity) {
        if(activationType == ActivationType.tick){
            String parsedCommand = this.command.replaceAll("\\{self\\}", mobEntity.getUuidAsString());
            Objects.requireNonNull(mobEntity.getServer()).getCommandManager().execute(mobEntity.getServer().getCommandSource(),parsedCommand);
        }
    }
}
