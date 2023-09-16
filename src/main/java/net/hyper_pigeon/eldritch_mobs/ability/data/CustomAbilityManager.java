package net.hyper_pigeon.eldritch_mobs.ability.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityHelper;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.ability.ActivationType;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class CustomAbilityManager extends JsonDataLoader implements IdentifiableResourceReloadListener {

    public CustomAbilityManager() {
        super(new Gson(), "ability");
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("ability");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        prepared.forEach((id, element) -> {
            JsonObject jsonObject = element.getAsJsonObject();

            if(!AbilityHelper.includesAbility(jsonObject.get("name").getAsString())) {
                ActivationType activationType = ActivationType.valueOf(jsonObject.get("activationType").getAsString());

                CustomAbility customAbility = new CustomAbility(
                        jsonObject.get("name").getAsString(),
                        AbilityType.valueOf(jsonObject.get("type").getAsString()),
                        AbilitySubType.valueOf(jsonObject.get("subtype").getAsString()),
                        activationType,
                        jsonObject.get("command").getAsString()
                );

                if (activationType == ActivationType.hasTarget) {
                    long cooldown = jsonObject.get("cooldown").getAsLong();
                    customAbility.setCooldown(cooldown);
                }

                AbilityHelper.addAbility(customAbility);
            }
        });
    }
}
