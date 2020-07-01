package net.hyper_pigeon.eldritch_mobs.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = "eldritch_mobs")
public class EldritchMobsConfig implements ConfigData {

    @Comment("Enable Water Creatures with Eldritch Modifiers")
    public boolean toggleEldritchWaterCreatures = false;
    @Comment("Enable Boss Mobs with Eldritch Modifiers")
    public boolean toggleEldritchBossMobs = false;
    @Comment("Turn off custom names")
    public boolean turnOffNames = false;

    @Comment("X out of 10")
    public int EliteSpawnRates = 1;

    @Comment("X out of 10 Elites")
    public int UltraSpawnRates = 1;

    @Comment("X out of 10 Ultras")
    public int EldritchSpawnRates = 1;

}
