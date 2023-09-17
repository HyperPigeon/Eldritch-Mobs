package net.hyper_pigeon.eldritch_mobs.register;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class EldritchMobTagKeys {
    private static final Identifier BLACKLIST_ID = new Identifier("eldritch_mobs:blacklist");
    public static final TagKey<EntityType<?>> BLACKLIST = TagKey.of(RegistryKeys.ENTITY_TYPE, BLACKLIST_ID);

    private static final Identifier ALLOWED_ID = new Identifier("eldritch_mobs:allowed");
    public static final TagKey<EntityType<?>> ALLOWED = TagKey.of(RegistryKeys.ENTITY_TYPE, ALLOWED_ID);

    private static final Identifier ALWAYS_ELITE_ID = new Identifier("eldritch_mobs:always_elite");
    public static final TagKey<EntityType<?>> ALWAYS_ELITE = TagKey.of(RegistryKeys.ENTITY_TYPE, ALWAYS_ELITE_ID);

    private static final Identifier ALWAYS_ULTRA_ID = new Identifier("eldritch_mobs:always_ultra");
    public static final TagKey<EntityType<?>> ALWAYS_ULTRA = TagKey.of(RegistryKeys.ENTITY_TYPE, ALWAYS_ULTRA_ID);

    private static final Identifier ALWAYS_ELDRITCH_ID = new Identifier("eldritch_mobs:always_eldritch");
    public static final TagKey<EntityType<?>> ALWAYS_ELDRITCH = TagKey.of(RegistryKeys.ENTITY_TYPE, ALWAYS_ELDRITCH_ID);
}
