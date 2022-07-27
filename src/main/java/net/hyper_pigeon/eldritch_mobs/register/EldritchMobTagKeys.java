package net.hyper_pigeon.eldritch_mobs.register;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class EldritchMobTagKeys {
    private static final Identifier BLACKLIST_ID = new Identifier("eldritch_mobs:blacklist");
    public static final Tag<EntityType<?>> BLACKLIST = TagFactory.ENTITY_TYPE.create(BLACKLIST_ID);

    private static final Identifier ALLOWED_ID = new Identifier("eldritch_mobs:allowed");
    public static final Tag<EntityType<?>> ALLOWED = TagFactory.ENTITY_TYPE.create(ALLOWED_ID);

    private static final Identifier ALWAYS_ELITE_ID = new Identifier("eldritch_mobs:always_elite");
    public static final Tag<EntityType<?>> ALWAYS_ELITE = TagFactory.ENTITY_TYPE.create(ALWAYS_ELITE_ID);

    private static final Identifier ALWAYS_ULTRA_ID = new Identifier("eldritch_mobs:always_ultra");
    public static final Tag<EntityType<?>> ALWAYS_ULTRA = TagFactory.ENTITY_TYPE.create(ALWAYS_ULTRA_ID);

    private static final Identifier ALWAYS_ELDRITCH_ID = new Identifier("eldritch_mobs:always_eldritch");
    public static final Tag<EntityType<?>> ALWAYS_ELDRITCH = TagFactory.ENTITY_TYPE.create(ALWAYS_ELDRITCH_ID);
}
