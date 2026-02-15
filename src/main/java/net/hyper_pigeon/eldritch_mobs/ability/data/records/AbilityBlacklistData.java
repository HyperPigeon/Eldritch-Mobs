package net.hyper_pigeon.eldritch_mobs.ability.data.records;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.List;

public record AbilityBlacklistData(
        String name,
        List<Identifier> entities
) {
    public static final Codec<AbilityBlacklistData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("name").forGetter(AbilityBlacklistData::name),
                    Identifier.CODEC.listOf().fieldOf("entities").forGetter(AbilityBlacklistData::entities)
            ).apply(instance, AbilityBlacklistData::new));
}

