package net.hyper_pigeon.eldritch_mobs.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import net.hyper_pigeon.eldritch_mobs.mod_components.modifiers.ModifierComponent;

@Config(name = "eldritch_mobs")
public class EldritchMobsConfig implements ConfigData {

    @Comment("Enable Water Creatures with Eldritch Modifiers")
    public boolean toggleEldritchWaterCreatures = false;
    @Comment("Enable Boss Mobs with Eldritch Modifiers")
    public boolean toggleEldritchBossMobs = false;
    @Comment("Turn off custom names")
    public boolean turnOffNames = false;

    @Comment("Out of 100 Mobs")
    public int EliteSpawnRates = 10;

    @Comment("Out of 100 Elites")
    public int UltraSpawnRates = 10;

    @Comment("Out of 100 Ultras")
    public int EldritchSpawnRates = 10;

    @ConfigEntry.Gui.CollapsibleObject
    public DisableModifiers disableModifiers = new DisableModifiers();

    public static class DisableModifiers {
        public static boolean disableAlchemist = false;
        public static boolean disableBeserk = false;
        public static boolean disableYeeter = false;
        public static boolean disableBlinding = false;
        public static boolean disableBurning = false;
        public static boolean disableCloaked = false;
        public static boolean disableDeflector = false;
        public static boolean disableDraining = false;
        public static boolean disableDrowning = false;
        public static boolean disableEnder = false;
        public static boolean disableGhastly = false;
        public static boolean disableGravity = false;
        public static boolean disableLethargic = false;
        public static boolean disableLifesteal = false;
        public static boolean disableOneUp = false;
        public static boolean disableRegen = false;
        public static boolean disableResistant = false;
        public static boolean disableRust = false;
        public static boolean disableSnatcher = false;
        public static boolean disableSpeedster = false;
        public static boolean disableSprinter = false;
        public static boolean disableStarving = false;
        public static boolean disableStormy = false;
        public static boolean disableThorny = false;
        public static boolean disableToxic = false;
        public static boolean disableWeakness = false;
        public static boolean disableWebslinging = false;
        public static boolean disableWithering = false;
        public static boolean disableSniper = false;
    }

    public void removeMods(){
        if(DisableModifiers.disableAlchemist){
            ModifierComponent.all_mods.remove("alchemist");
        }
        if(DisableModifiers.disableBeserk){
            ModifierComponent.all_mods.remove("beserk");
        }
        if(DisableModifiers.disableYeeter){
            ModifierComponent.all_mods.remove("yeeter");
        }
        if(DisableModifiers.disableBlinding){
            ModifierComponent.all_mods.remove("blinding");
        }
        if(DisableModifiers.disableBurning){
            ModifierComponent.all_mods.remove("burning");
        }
        if(DisableModifiers.disableCloaked){
            ModifierComponent.all_mods.remove("cloaked");
        }
        if(DisableModifiers.disableDeflector){
            ModifierComponent.all_mods.remove("deflector");
        }
        if(DisableModifiers.disableDraining){
            ModifierComponent.all_mods.remove("draining");
        }
        if(DisableModifiers.disableDrowning){
            ModifierComponent.all_mods.remove("drowning");
        }
        if(DisableModifiers.disableEnder){
            ModifierComponent.all_mods.remove("ender");
        }
        if(DisableModifiers.disableGhastly){
            ModifierComponent.all_mods.remove("ghastly");
        }
        if(DisableModifiers.disableGravity){
            ModifierComponent.all_mods.remove("gravity");
        }
        if(DisableModifiers.disableLethargic){
            ModifierComponent.all_mods.remove("lethargic");
        }
        if(DisableModifiers.disableLifesteal){
            ModifierComponent.all_mods.remove("lifesteal");
        }
        if(DisableModifiers.disableOneUp){
            ModifierComponent.all_mods.remove("one_up");
        }
        if(DisableModifiers.disableRegen){
            ModifierComponent.all_mods.remove("regen");
        }
        if(DisableModifiers.disableResistant){
            ModifierComponent.all_mods.remove("resistant");
        }
        if(DisableModifiers.disableRust){
            ModifierComponent.all_mods.remove("rust");
        }
        if(DisableModifiers.disableSnatcher){
            ModifierComponent.all_mods.remove("snatcher");
        }
        if(DisableModifiers.disableSpeedster){
            ModifierComponent.all_mods.remove("speedster");
        }
        if(DisableModifiers.disableSprinter){
            ModifierComponent.all_mods.remove("sprinter");
        }
        if(DisableModifiers.disableStarving){
            ModifierComponent.all_mods.remove("starving");
        }
        if(DisableModifiers.disableStormy){
            ModifierComponent.all_mods.remove("stormy");
        }
        if(DisableModifiers.disableThorny){
            ModifierComponent.all_mods.remove("thorny");
        }
        if(DisableModifiers.disableToxic){
            ModifierComponent.all_mods.remove("toxic");
        }
        if(DisableModifiers.disableWeakness){
            ModifierComponent.all_mods.remove("weakness");
        }
        if(DisableModifiers.disableWebslinging){
            ModifierComponent.all_mods.remove("webslinging");
        }
        if(DisableModifiers.disableWithering){
            ModifierComponent.all_mods.remove("withering");
        }
        if(DisableModifiers.disableSniper){
            ModifierComponent.all_mods.remove("sniper");
        }
    }



}
