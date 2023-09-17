package net.hyper_pigeon.eldritch_mobs.ability.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbilityBlacklistManager extends JsonDataLoader implements IdentifiableResourceReloadListener {
    public AbilityBlacklistManager() {
        super(new Gson(), "ability_blacklist");
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("ability_blacklist");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        prepared.forEach((id, element) -> {
            JsonObject jsonObject = element.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();

            List<EntityType<?>> entityTypeList = new ArrayList<>();
            for (var entry : element.getAsJsonObject().get("entities").getAsJsonArray()) {
                String namespace = entry.getAsString();
                if (namespace.contains("minecraft:")) {
                    String[] split_namespace = namespace.split(":");
                    entityTypeList.add(Registries.ENTITY_TYPE.get(Identifier.tryParse(split_namespace[1])));
                } else {
                    entityTypeList.add(Registries.ENTITY_TYPE.get(Identifier.tryParse(namespace)));
                }
            }

            AbilityHelper.addBlacklist(name, entityTypeList);

        });
    }
}
