package net.hyper_pigeon.eldritch_mobs.ability.data.records;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AbilityConfigurationData(
        String name,
        String activationType,
        String type,
        String subtype,
        String command,
        long cooldown

) {
    public static final Codec<AbilityConfigurationData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("name").forGetter(AbilityConfigurationData::name),
                    Codec.STRING.fieldOf("activationType").forGetter(AbilityConfigurationData::activationType),
                    Codec.STRING.fieldOf("type").forGetter(AbilityConfigurationData::type),
                    Codec.STRING.fieldOf("subtype").forGetter(AbilityConfigurationData::subtype),
                    Codec.STRING.fieldOf("command").forGetter(AbilityConfigurationData::command),
                    Codec.LONG.fieldOf("cooldown").forGetter(AbilityConfigurationData::cooldown)
            ).apply(instance, AbilityConfigurationData::new));
}
