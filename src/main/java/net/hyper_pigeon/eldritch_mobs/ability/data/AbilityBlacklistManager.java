package net.hyper_pigeon.eldritch_mobs.ability.data;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityHelper;
import net.hyper_pigeon.eldritch_mobs.ability.data.records.AbilityBlacklistData;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbilityBlacklistManager extends JsonDataLoader<AbilityBlacklistData> implements IdentifiableResourceReloadListener {
    public AbilityBlacklistManager() {
        super(AbilityBlacklistData.CODEC, "ability_blacklist");
    }

    @Override
    public Identifier getFabricId() {
        return Identifier.of("ability_blacklist");
    }

    @Override
    protected void apply(Map<Identifier, AbilityBlacklistData> prepared , ResourceManager manager, Profiler profiler) {
        prepared.forEach((id, data) -> {

            List<EntityType<?>> entityTypeList = new ArrayList<>();

            for (Identifier identifier : data.entities()) {
                entityTypeList.add(Registries.ENTITY_TYPE.get(identifier));
            }

            AbilityHelper.addBlacklist(data.name(), entityTypeList);
        });
    }
}
