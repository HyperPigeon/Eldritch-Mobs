package net.hyper_pigeon.eldritch_mobs.mod_components.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;

public interface ModifierInterface extends Component {


    void useAbility(MobEntity entity);

    void setRank();

    void setMods();

    boolean hasMod(String name);

    void damageActivatedMod(LivingEntity entity, DamageSource source, float amount);

    boolean isEldritch();

    String get_mod_string();

    boolean isElite();

    boolean isUltra();

    void setIs_elite(boolean bool);

    void setIs_ultra(boolean bool);

    void setIs_eldritch(boolean bool);

    void spawnedInLampChunk();



}
