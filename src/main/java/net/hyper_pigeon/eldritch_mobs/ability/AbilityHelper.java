package net.hyper_pigeon.eldritch_mobs.ability;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.active.defensive.SprinterAbility;
import net.hyper_pigeon.eldritch_mobs.ability.active.offensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.defensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.offensive.*;
import net.minecraft.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class AbilityHelper {

    public static final class AbilityRecord<C> {
        public final Ability ability;
        public final @Nullable C config;
        public final String name;

        public Class<C> getConfigType() {
            //noinspection unchecked
            return config == null ? null : (Class<C>) config.getClass();
        }

        public AbilityRecord(Ability ability, @Nullable C config) {
            this.ability = ability;
            this.config = config;
            this.name = ability.getName();
        }
    }

    public static final ArrayList<AbilityRecord<?>> ALL_ABILITIES = new ArrayList<>() {{
        add(new AbilityRecord<>(new AlchemistAbility()   , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.alchemistConfig   ));
        add(new AbilityRecord<>(new BlindingAbility()    , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.blindingConfig    ));
        add(new AbilityRecord<>(new BurningAbility()     , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.burningConfig     ));
        add(new AbilityRecord<>(new DrainingAbility()    , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.drainingConfig    ));
        add(new AbilityRecord<>(new DrowningAbility()    , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.drowningConfig    ));
        add(new AbilityRecord<>(new DuplicatorAbility()  , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.duplicatorConfig  ));
        add(new AbilityRecord<>(new GhastlyAbility()     , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.ghastlyConfig     ));
        add(new AbilityRecord<>(new GravityAbility()     , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.gravityConfig     ));
        add(new AbilityRecord<>(new LethargicAbility()   , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.lethargicConfig   ));
        add(new AbilityRecord<>(new RustAbility()        , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.rustConfig        ));
        add(new AbilityRecord<>(new StarvingAbility()    , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.speedsterConfig   ));
        add(new AbilityRecord<>(new StormyAbility()      , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.stormyConfig      ));
        add(new AbilityRecord<>(new WeaknessAbility()    , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.weaknessConfig    ));
        add(new AbilityRecord<>(new WebslingingAbility() , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.webslingingConfig ));
        add(new AbilityRecord<>(new CloakedAbility()     , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.cloakedConfig     ));
        add(new AbilityRecord<>(new DeflectorAbility()   , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.deflectorConfig   ));
        add(new AbilityRecord<>(new EnderAbility()       , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.enderConfig       ));
        add(new AbilityRecord<>(new UndyingAbility()     , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.undyingConfig     ));
        add(new AbilityRecord<>(new RegeneratingAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.regeneratingConfig));
        add(new AbilityRecord<>(new ResistantAbility()   , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.resistantConfig   ));
        add(new AbilityRecord<>(new SprinterAbility()    , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.sprinterConfig    ));
        add(new AbilityRecord<>(new StarvingAbility()    , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.starvingConfig    ));
        add(new AbilityRecord<>(new ThornyAbility()      , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.thornyConfig      ));
        add(new AbilityRecord<>(new ToxicAbility()       , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.toxicConfig       ));
        add(new AbilityRecord<>(new WitheringAbility()   , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.witheringConfig   ));
        add(new AbilityRecord<>(new BerserkAbility()     , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.berserkConfig     ));
        add(new AbilityRecord<>(new LifestealAbility()   , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.lifestealConfig   ));
        add(new AbilityRecord<>(new SpeedsterAbility()   , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.speedsterConfig   ));
        add(new AbilityRecord<>(new YeeterAbility()      , EldritchMobsMod.ELDRITCH_MOBS_CONFIG.yeeterConfig      ));
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
        ALL_ABILITIES.add(new AbilityRecord<>(ability, null));
    }

    public static boolean includesAbility(String abilityName) {
        return ALL_ABILITIES.stream().anyMatch(abilityRecord -> abilityRecord.name.equals(abilityName));
    }

    public static void addBlacklist(String name, List<EntityType<?>> entityTypeList) {
        ABILITY_BLACKLIST.put(name, entityTypeList);
    }

    public static void removeAbility(Ability ability) {
        ALL_ABILITIES.removeIf(abilityRecord -> abilityRecord.name.equals(ability.getName()));
    }

    public static void removeAbilityByName(String name) {
        ALL_ABILITIES.removeIf(abilityRecord -> abilityRecord.name.equals(name));
    }

    public static Optional<AbilityRecord<?>> getAbilityRecordByName(String name) {
        return AbilityHelper.ALL_ABILITIES.stream()
                .filter(record -> record.name.equals(name))
                .findFirst();
    }

    public static List<String> getAbilityNames() {
        return ALL_ABILITIES.stream().map(record -> record.name).collect(Collectors.toList());
    }

    public static List<Ability> getAbilities() {
        return ALL_ABILITIES.stream().map(record -> record.ability).collect(Collectors.toList());
    }

    public static void removeDisabledAbilities() {
        ArrayList<String> abilitiesToRemove = new ArrayList<>();
        for (AbilityRecord<?> record : ALL_ABILITIES) {
            try { if (record.config != null && record.config.getClass().getField("disabled").getBoolean(record.config)) abilitiesToRemove.add(record.name);}
            catch (IllegalAccessException | NoSuchFieldException e) { throw new RuntimeException(e); }
        }

        for(String name : abilitiesToRemove){
            removeAbilityByName(name);
        }
    }
}
