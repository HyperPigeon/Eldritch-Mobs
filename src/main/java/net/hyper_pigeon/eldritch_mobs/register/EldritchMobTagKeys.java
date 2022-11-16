package net.hyper_pigeon.eldritch_mobs.register;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EldritchMobTagKeys {
    private static final Identifier BLACKLIST_ID = new Identifier("eldritch_mobs:blacklist");
    public static final TagKey<EntityType<?>> BLACKLIST = TagKey.of(Registry.ENTITY_TYPE_KEY, BLACKLIST_ID);

    private static final Identifier ALLOWED_ID = new Identifier("eldritch_mobs:allowed");
    public static final TagKey<EntityType<?>> ALLOWED = TagKey.of(Registry.ENTITY_TYPE_KEY, ALLOWED_ID);

    private static final Identifier ALWAYS_ELITE_ID = new Identifier("eldritch_mobs:always_elite");
    public static final TagKey<EntityType<?>> ALWAYS_ELITE = TagKey.of(Registry.ENTITY_TYPE_KEY, ALWAYS_ELITE_ID);

    private static final Identifier ALWAYS_ULTRA_ID = new Identifier("eldritch_mobs:always_ultra");
    public static final TagKey<EntityType<?>> ALWAYS_ULTRA = TagKey.of(Registry.ENTITY_TYPE_KEY, ALWAYS_ULTRA_ID);

    private static final Identifier ALWAYS_ELDRITCH_ID = new Identifier("eldritch_mobs:always_eldritch");
    public static final TagKey<EntityType<?>> ALWAYS_ELDRITCH = TagKey.of(Registry.ENTITY_TYPE_KEY, ALWAYS_ELDRITCH_ID);
}
