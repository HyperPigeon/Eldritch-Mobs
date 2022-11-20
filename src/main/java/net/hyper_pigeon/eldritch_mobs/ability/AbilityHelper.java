package net.hyper_pigeon.eldritch_mobs.ability;

import net.hyper_pigeon.eldritch_mobs.ability.active.defensive.SprinterAbility;
import net.hyper_pigeon.eldritch_mobs.ability.active.offensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.defensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.offensive.*;
import net.hyper_pigeon.eldritch_mobs.config.AbilitySpecificConfig;
import net.minecraft.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

import static net.hyper_pigeon.eldritch_mobs.EldritchMobsMod.ELDRITCH_MOBS_CONFIG;

public class AbilityHelper {

    public static final class AbilityRecord<C> {
        public final Ability ability;
        public final @Nullable C config;
        public final String name;

        public AbilityRecord(Ability ability, @Nullable C config) {
            this.ability = ability;
            this.config = config;
            this.name = ability.getName();
        }
    }

    public static final HashMap<Ability, @Nullable AbilitySpecificConfig> ALL_ABILITIES = new HashMap<>() {{
        put(new AlchemistAbility()   , ELDRITCH_MOBS_CONFIG.alchemistConfig   );
        put(new BlindingAbility()    , ELDRITCH_MOBS_CONFIG.blindingConfig    );
        put(new BurningAbility()     , ELDRITCH_MOBS_CONFIG.burningConfig     );
        put(new DrainingAbility()    , ELDRITCH_MOBS_CONFIG.drainingConfig    );
        put(new DrowningAbility()    , ELDRITCH_MOBS_CONFIG.drowningConfig    );
        put(new DuplicatorAbility()  , ELDRITCH_MOBS_CONFIG.duplicatorConfig  );
        put(new GhastlyAbility()     , ELDRITCH_MOBS_CONFIG.ghastlyConfig     );
        put(new GravityAbility()     , ELDRITCH_MOBS_CONFIG.gravityConfig     );
        put(new LethargicAbility()   , ELDRITCH_MOBS_CONFIG.lethargicConfig   );
        put(new RustAbility()        , ELDRITCH_MOBS_CONFIG.rustConfig        );
        put(new StarvingAbility()    , ELDRITCH_MOBS_CONFIG.speedsterConfig   );
        put(new StormyAbility()      , ELDRITCH_MOBS_CONFIG.stormyConfig      );
        put(new WeaknessAbility()    , ELDRITCH_MOBS_CONFIG.weaknessConfig    );
        put(new WebslingingAbility() , ELDRITCH_MOBS_CONFIG.webslingingConfig );
        put(new CloakedAbility()     , ELDRITCH_MOBS_CONFIG.cloakedConfig     );
        put(new DeflectorAbility()   , ELDRITCH_MOBS_CONFIG.deflectorConfig   );
        put(new EnderAbility()       , ELDRITCH_MOBS_CONFIG.enderConfig       );
        put(new UndyingAbility()     , ELDRITCH_MOBS_CONFIG.undyingConfig     );
        put(new RegeneratingAbility(), ELDRITCH_MOBS_CONFIG.regeneratingConfig);
        put(new ResistantAbility()   , ELDRITCH_MOBS_CONFIG.resistantConfig   );
        put(new SprinterAbility()    , ELDRITCH_MOBS_CONFIG.sprinterConfig    );
        put(new StarvingAbility()    , ELDRITCH_MOBS_CONFIG.starvingConfig    );
        put(new ThornyAbility()      , ELDRITCH_MOBS_CONFIG.thornyConfig      );
        put(new ToxicAbility()       , ELDRITCH_MOBS_CONFIG.toxicConfig       );
        put(new WitheringAbility()   , ELDRITCH_MOBS_CONFIG.witheringConfig   );
        put(new BerserkAbility()     , ELDRITCH_MOBS_CONFIG.berserkConfig     );
        put(new LifestealAbility()   , ELDRITCH_MOBS_CONFIG.lifestealConfig   );
        put(new SpeedsterAbility()   , ELDRITCH_MOBS_CONFIG.speedsterConfig   );
        put(new YeeterAbility()      , ELDRITCH_MOBS_CONFIG.yeeterConfig      );
    }};

    public static final HashMap<String, List<EntityType<?>>> ABILITY_BLACKLIST = new HashMap<>();

    public static final Random RANDOM = new Random();

    public static List<Ability> pickNRandom(List<Ability> l, int n) {
        List<Ability> copy = new ArrayList<>(l);
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
    }

    public static List<Ability> pickNRandomForEntity(List<Ability> lst, int n, EntityType<?> entityType) {
        List<Ability> copy = new ArrayList<>(lst);
        copy.removeIf(ability -> ABILITY_BLACKLIST.containsKey(ability.getName()) && ABILITY_BLACKLIST.get(ability.getName()).contains(entityType));
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
    }

    public static void addAbility(Ability ability) {
        ALL_ABILITIES.put(ability, null);
    }

    public static void addBlacklist(String name, List<EntityType<?>> entityTypeList) {
        ABILITY_BLACKLIST.put(name, entityTypeList);
    }

    public static void removeAbility(Ability ability) {
        ALL_ABILITIES.remove(ability);
    }

    public static void removeAbilityByName(String name) {
        ALL_ABILITIES.keySet().removeIf(k -> k.getName().equals(name));
    }

    public static List<String> getAbilityNames() {
        return ALL_ABILITIES.keySet().stream().map(Ability::getName).collect(Collectors.toList());
    }

    public static List<Ability> getAbilities() { return ALL_ABILITIES.keySet().stream().toList(); }

    public static void removeDisabledAbilities() {
        ALL_ABILITIES.values().stream()
                .filter(Objects::nonNull)
                .filter(c -> c.disabled)
                .map(c -> c.name)
                .forEach(AbilityHelper::removeAbilityByName);
    }
}
