package net.hyper_pigeon.eldritch_mobs.ability.data;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityHelper;
import net.hyper_pigeon.eldritch_mobs.ability.AbilitySubType;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityType;
import net.hyper_pigeon.eldritch_mobs.ability.ActivationType;
import net.hyper_pigeon.eldritch_mobs.ability.data.records.AbilityConfigurationData;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class CustomAbilityManager extends JsonDataLoader<AbilityConfigurationData> implements IdentifiableResourceReloadListener {

    public CustomAbilityManager() {
        super(AbilityConfigurationData.CODEC, ResourceFinder.json("ability"));
    }

    @Override
    public Identifier getFabricId() {
        return Identifier.of("ability");
    }

    @Override
    protected void apply(Map<Identifier, AbilityConfigurationData> prepared, ResourceManager manager, Profiler profiler) {

        prepared.forEach((id, data) -> {

            if (!AbilityHelper.includesAbility(data.name())) {

                ActivationType activationType =
                        ActivationType.valueOf(data.activationType());

                CustomAbility customAbility = new CustomAbility(
                        data.name(),
                        AbilityType.valueOf(data.type()),
                        AbilitySubType.valueOf(data.subtype()),
                        activationType,
                        data.command()
                );

                if (activationType == ActivationType.hasTarget) {
                    customAbility.setCooldown(data.cooldown());
                }

                AbilityHelper.addAbility(customAbility);
            }
        });
    }

}
