package net.hyper_pigeon.eldritch_mobs.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import static net.hyper_pigeon.eldritch_mobs.EldritchMobsMod.MOD_ID;

@Config(name = MOD_ID)
public class EldritchMobsConfig implements ConfigData {

    public double EliteSpawnRates = 0.01;

    public double UltraSpawnRates = 0.001;

    public double EldritchSpawnRates = 0.0001;

    public float EliteHealthMultiplier = 4;
    public float UltraHealthMultiplier = 6;
    public float EldritchHealthMultiplier = 8;

    public int EliteXpMultiplier = 4;
    public int UltraXpMultiplier = 6;
    public int EldritchXpMultiplier = 8;

    public int EliteMinModifiers = 2;
    public int UltraMinModifiers = 5;
    public int EldritchMinModifiers = 9;

    public int EliteMaxModifiers = 4;
    public int UltraMaxModifiers = 8;
    public int EldritchMaxModifiers = 12;

    @Comment("Prevent named mobs from becoming Elite, Ultra, and Eldritch")
    public boolean ignoreNamedMobs = true;

    @Comment("Turn off titles for mobs")
    public boolean turnOffTitles = false;

    @Comment("Turn off boss bars")
    public boolean turnOffBossBars = false;

    @Comment("Boss bars appears when placing crosshair on buffed mob")
    public boolean crosshairBossBars = true;

    @Comment("Buffed mobs are highlighted after being damaged")
    public boolean turnOnGlowingMobs = false;

    @Comment("Turn off loot for buffed mobs")
    public boolean disableLootDrops = false;

    @Comment("If true, buffed mobs only drop loot when killed by players")
    public boolean onlyDropLootIfKilledByPlayers = true;

    @Comment("Buffed mobs also drop the loot of the tier below them + the loot of their tier.")
    public boolean combinedLootDrop = true;

    @Comment("Buffed mobs have generic titles w/o abilities")
    public boolean genericTitles = false;

    @ConfigEntry.Gui.CollapsibleObject
    public AlchemistConfig alchemistConfig = new AlchemistConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public BerserkConfig berserkConfig = new BerserkConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public BlindingConfig blindingConfig = new BlindingConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public BurningConfig burningConfig = new BurningConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public CloakedConfig cloakedConfig = new CloakedConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public DeflectorConfig deflectorConfig = new DeflectorConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public DrainingConfig drainingConfig = new DrainingConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public DrowningConfig drowningConfig = new DrowningConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public DuplicatorConfig duplicatorConfig = new DuplicatorConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public EnderConfig enderConfig = new EnderConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public GhastlyConfig ghastlyConfig = new GhastlyConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public GravityConfig gravityConfig = new GravityConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public LethargicConfig lethargicConfig = new LethargicConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public LifestealConfig lifestealConfig = new LifestealConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public RegeneratingConfig regeneratingConfig = new RegeneratingConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public RustConfig rustConfig = new RustConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public ResistantConfig resistantConfig = new ResistantConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public StarvingConfig starvingConfig = new StarvingConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public StormyConfig stormyConfig = new StormyConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public SpeedsterConfig speedsterConfig = new SpeedsterConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public SprinterConfig sprinterConfig = new SprinterConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public ThornyConfig thornyConfig = new ThornyConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public ToxicConfig toxicConfig = new ToxicConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public UndyingConfig undyingConfig = new UndyingConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public WeaknessConfig weaknessConfig = new WeaknessConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public WebslingingConfig webslingingConfig = new WebslingingConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public WitheringConfig witheringConfig = new WitheringConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public YeeterConfig yeeterConfig = new YeeterConfig();

    public static class AlchemistConfig extends AbilitySpecificConfig {

        public long cooldown = 200;
        public boolean useStrongHealing = false;
        public boolean useStrongHarming = false;

        public AlchemistConfig() { super("Alchemist", false); }
    }

    public static class BerserkConfig extends AbilitySpecificConfig {

        public int amplifier = 1;

        public BerserkConfig() { super("Berserk", false); }
    }

    public static class BlindingConfig extends AbilitySpecificConfig {

        public long cooldown = 300;
        public int duration = 80;
        public int amplifier = 0;

        public BlindingConfig() { super("Blinding", false); }
    }

    public static class BurningConfig extends AbilitySpecificConfig {

        public long cooldown = 500;
        public int fireDuration = 6;

        public BurningConfig() { super("Burning", false); }
    }

    public static class CloakedConfig extends AbilitySpecificConfig {

        public CloakedConfig() { super("Cloaked", false); }
    }

    public static class DeflectorConfig extends AbilitySpecificConfig {

        public DeflectorConfig() { super("Deflector", false); }
    }

    public static class DrainingConfig extends AbilitySpecificConfig {

        public long cooldown = 300;
        public int duration = 125;
        public int amplifier = 0;

        public DrainingConfig() { super("Draining", false); }
    }

    public static class DrowningConfig extends AbilitySpecificConfig {

        public float drowningDamage = 0.010f;
        public int waterBreathingDuration = 100;

        public DrowningConfig() { super("Drowning", false); }
    }

    public static class DuplicatorConfig extends AbilitySpecificConfig {

        public long cooldown = 3000;

        public DuplicatorConfig() { super("Duplicator", false); }
    }

    public static class EnderConfig extends AbilitySpecificConfig {

        public EnderConfig() { super("Ender", false); }
    }

    public static class GhastlyConfig extends AbilitySpecificConfig {

        public long cooldown = 700;
        public int fireballPower = 1;

        public GhastlyConfig() { super("Ghastly", false); }
    }

    public static class GravityConfig extends AbilitySpecificConfig {

        public long cooldown = 700;

        public GravityConfig() { super("Gravity", false); }
    }

    public static class LethargicConfig extends AbilitySpecificConfig {

        public long cooldown = 250;
        public int duration = 100;
        public int amplifier = 0;

        public LethargicConfig() { super("Lethargic", false); }
    }

    public static class LifestealConfig extends AbilitySpecificConfig {

        public double lifestealHealProportion = 1;

        public LifestealConfig() { super("Lifesteal", false); }
    }

    public static class RegeneratingConfig extends AbilitySpecificConfig {

        public float healAmount = 0.025f;

        public RegeneratingConfig() { super("Regenerating", false); }
    }

    public static class RustConfig extends AbilitySpecificConfig {

        public int equipmentDamage = 30;

        public RustConfig() { super("Rust", false); }
    }

    public static class ResistantConfig extends AbilitySpecificConfig {

        public int amplifier = 0;

        public ResistantConfig() { super("Resistant", false); }
    }

    public static class StarvingConfig extends AbilitySpecificConfig {

        public long cooldown = 250;
        public int duration = 200;
        public int amplifier = 0;

        public StarvingConfig() { super("Starving", false); }
    }

    public static class StormyConfig extends AbilitySpecificConfig {

        @ConfigEntry.BoundedDiscrete(min = -1, max = 128)
        public int lightningRodRadius = 16;
        public long cooldown = 1000;

        public StormyConfig() { super("Stormy", false); }
    }

    public static class SpeedsterConfig extends AbilitySpecificConfig {

        public int amplifier = 2;

        public SpeedsterConfig() { super("Speedster", false); }
    }

    public static class SprinterConfig extends AbilitySpecificConfig {

        public long cooldown = 600;
        public int duration = 100;
        public int amplifier = 4;

        public SprinterConfig() { super("Sprinter", false); }
    }

    public static class ThornyConfig extends AbilitySpecificConfig {

        public double thornyReturnDamage = 0.25;

        public ThornyConfig() { super("Thorny", false); }
    }

    public static class ToxicConfig extends AbilitySpecificConfig {

        public int duration = 100;
        public int amplifier = 0;

        public ToxicConfig() { super("Toxic", false); }
    }

    public static class UndyingConfig extends AbilitySpecificConfig {

        public int regenerationDuration = 900;
        public int regenerationAmplifier = 1;
        public int absorptionDuration = 1200;
        public int absorptionAmplifier = 5;
        public int fireResistanceDuration = 800;

        public UndyingConfig() { super("Undying", false); }
    }

    public static class WeaknessConfig extends AbilitySpecificConfig {

        public long cooldown = 250;
        public int duration = 100;
        public int amplifier = 0;

        public WeaknessConfig() { super("Weakness", false); }
    }

    public static class WebslingingConfig extends AbilitySpecificConfig {

        public long cooldown = 700;

        public WebslingingConfig() { super("Webslinging", false); }
    }

    public static class WitheringConfig extends AbilitySpecificConfig {

        public int duration = 100;
        public int amplifier = 0;

        public WitheringConfig() { super("Withering", false); }
    }

    public static class YeeterConfig extends AbilitySpecificConfig {

        public double yeetAmount = 1.5;

        public YeeterConfig() { super("Yeeter", false); }
    }
}
