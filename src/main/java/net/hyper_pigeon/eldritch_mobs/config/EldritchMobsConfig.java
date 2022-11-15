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

    public static class AlchemistConfig {
        public String name = "Alchemist";
        public boolean disabled = false;
        public long cooldown = 200;
        public boolean useStrongHealing = false;
        public boolean useStrongHarming = false;
    }

    public static class BerserkConfig {
        public String name = "Berserk";
        public boolean disabled = false;
        public int amplifier = 1;
    }

    public static class BlindingConfig {
        public String name = "Blinding";
        public boolean disabled = false;
        public long cooldown = 300;
        public int duration = 80;
        public int amplifier = 0;
    }

    public static class BurningConfig {
        public String name = "Burning";
        public boolean disabled = false;
        public long cooldown = 500;
        public int fireDuration = 6;
    }

    public static class CloakedConfig {
        public String name = "Cloaked";
        public boolean disabled = false;
    }

    public static class DeflectorConfig {
        public String name = "Deflector";
        public boolean disabled = false;
    }

    public static class DrainingConfig {
        public String name = "Draining";
        public boolean disabled = false;
        public long cooldown = 300;
        public int duration = 125;
        public int amplifier = 0;
    }

    public static class DrowningConfig {
        public String name = "Drowning";
        public boolean disabled = false;
        public float drowningDamage = 0.010f;
        public int waterBreathingDuration = 100;
    }

    public static class DuplicatorConfig {
        public String name = "Duplicator";
        public boolean disabled = false;
        public long cooldown = 3000;
    }

    public static class EnderConfig {
        public String name = "Ender";
        public boolean disabled = false;
    }

    public static class GhastlyConfig {
        public String name = "Ghastly";
        public boolean disabled = false;
        public long cooldown = 700;
        public int fireballPower = 1;
    }

    public static class GravityConfig {
        public String name = "Gravity";
        public boolean disabled = false;
        public long cooldown = 700;
    }

    public static class LethargicConfig {
        public String name = "Lethargic";
        public boolean disabled = false;
        public long cooldown = 250;
        public int duration = 100;
        public int amplifier = 0;
    }

    public static class LifestealConfig {
        public String name = "Lifesteal";
        public boolean disabled = false;
        public double lifestealHealProportion = 1;
    }

    public static class RegeneratingConfig {
        public String name = "Regenerating";
        public boolean disabled = false;
        public float healAmount = 0.025f;
    }

    public static class RustConfig {
        public String name = "Rust";
        public boolean disabled = false;
        public int equipmentDamage = 30;
    }

    public static class ResistantConfig {
        public String name = "Resistant";
        public boolean disabled = false;
        public int amplifier = 0;
    }

    public static class StarvingConfig {
        public String name = "Starving";
        public boolean disabled = false;
        public long cooldown = 250;
        public int duration = 200;
        public int amplifier = 0;
    }

    public static class StormyConfig {
        public String name = "Stormy";
        public boolean disabled = false;
        public long cooldown = 1000;
    }

    public static class SpeedsterConfig {
        public String name = "Speedster";
        public boolean disabled = false;
        public int amplifier = 2;
    }

    public static class SprinterConfig {
        public String name = "Sprinter";
        public boolean disabled = false;
        public long cooldown = 600;
        public int duration = 100;
        public int amplifier = 4;
    }

    public static class ThornyConfig {
        public String name = "Thorny";
        public boolean disabled = false;
        public double thornyReturnDamage = 0.25;
    }

    public static class ToxicConfig {
        public String name = "Toxic";
        public boolean disabled = false;
        public int duration = 100;
        public int amplifier = 0;
    }

    public static class UndyingConfig {
        public String name = "Undying";
        public boolean disabled = false;
        public int regenerationDuration = 900;
        public int regenerationAmplifier = 1;
        public int absorptionDuration = 1200;
        public int absorptionAmplifier = 5;
        public int fireResistanceDuration = 800;
    }

    public static class WeaknessConfig {
        public String name = "Weakness";
        public boolean disabled = false;
        public long cooldown = 250;
        public int duration = 100;
        public int amplifier = 0;
    }

    public static class WebslingingConfig {
        public String name = "Webslinging";
        public boolean disabled = false;
        public long cooldown = 700;
    }

    public static class WitheringConfig {
        public String name = "Withering";
        public boolean disabled = false;
        public int duration = 100;
        public int amplifier = 0;
    }

    public static class YeeterConfig {
        public String name = "Yeeter";
        public boolean disabled = false;
        public double yeetAmount = 1.5;
    }
}
