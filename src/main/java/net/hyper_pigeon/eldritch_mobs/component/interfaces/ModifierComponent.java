package net.hyper_pigeon.eldritch_mobs.component.interfaces;

import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.rank.MobRank;
import net.minecraft.entity.boss.ServerBossBar;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.List;

public interface ModifierComponent extends Component, ServerTickingComponent, AutoSyncedComponent {

    void randomlySetRank();

    void randomlySetModifiers();

    List<Ability> getModifiers();

    void clearModifiers();

    void setTitle();

    MobRank getRank();

    void setRank(MobRank mobRank);

    void increaseHealth();

    ServerBossBar getBossBar();

    @Override
    default boolean isRequiredOnClient() {
        return false;
    }
}
