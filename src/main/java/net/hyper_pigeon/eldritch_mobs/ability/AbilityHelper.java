package net.hyper_pigeon.eldritch_mobs.ability;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.active.defensive.SprinterAbility;
import net.hyper_pigeon.eldritch_mobs.ability.active.offensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.defensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.offensive.*;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;

import java.util.*;

public class AbilityHelper {

    public static final ArrayList<Ability> all_abilities = new ArrayList(
            Arrays.asList(new AlchemistAbility(), new BlindingAbility(),
                    new BurningAbility(), new DrainingAbility(), new DrowningAbility(),
                    new DuplicatorAbility(), new GhastlyAbility(), new GravityAbility(),
                    new LethargicAbility(), new RustAbility(),
                    new StarvingAbility(), new StormyAbility(), new WeaknessAbility(), new WebslingingAbility(),
                    new CloakedAbility(), new DeflectorAbility(), new EnderAbility(),
                    new UndyingAbility(), new RegeneratingAbility(), new ResistantAbility(),
                    new SprinterAbility()
                    ,new ThornyAbility(),
                    new ToxicAbility(), new WitheringAbility(), new BerserkAbility(), new LifestealAbility(),
                    new SpeedsterAbility(), new YeeterAbility()));

    public static final HashMap<String, Ability> abilityNames = new HashMap<>();
    public static final HashMap<Ability, Boolean> abilityStatus = new HashMap<>();

    public static final HashMap<String, List<EntityType<?>>> abilityBlacklist = new HashMap<>();

    static {
        abilityNames.put(new AlchemistAbility().getName(),new AlchemistAbility());
        abilityNames.put(new BlindingAbility().getName(),new BlindingAbility());
        abilityNames.put(new BurningAbility().getName(), new BurningAbility());
        abilityNames.put(new DrainingAbility().getName(), new DrainingAbility());
        abilityNames.put(new DrowningAbility().getName(), new DrowningAbility());
        abilityNames.put(new DuplicatorAbility().getName(), new DuplicatorAbility());
        abilityNames.put(new GhastlyAbility().getName(), new GhastlyAbility());
        abilityNames.put(new GravityAbility().getName(), new GravityAbility());
        abilityNames.put(new LethargicAbility().getName(), new LethargicAbility());
        abilityNames.put(new RustAbility().getName(), new RustAbility());
        abilityNames.put(new StarvingAbility().getName(), new StarvingAbility());
        abilityNames.put(new StormyAbility().getName(), new StormyAbility());
        abilityNames.put(new WeaknessAbility().getName(), new WeaknessAbility());
        abilityNames.put(new WebslingingAbility().getName(), new WebslingingAbility());
        abilityNames.put(new CloakedAbility().getName(), new CloakedAbility());
        abilityNames.put(new DeflectorAbility().getName(), new DeflectorAbility());
        abilityNames.put(new EnderAbility().getName(),new EnderAbility());
        abilityNames.put(new UndyingAbility().getName(), new UndyingAbility());
        abilityNames.put(new RegeneratingAbility().getName(), new RegeneratingAbility());
        abilityNames.put(new ResistantAbility().getName(), new ResistantAbility());
        abilityNames.put(new SprinterAbility().getName(), new SprinterAbility());
        abilityNames.put(new ThornyAbility().getName(), new ThornyAbility());
        abilityNames.put(new ToxicAbility().getName(), new ToxicAbility());
        abilityNames.put(new WitheringAbility().getName(), new WitheringAbility());
        abilityNames.put(new BerserkAbility().getName(), new BerserkAbility());
        abilityNames.put(new LifestealAbility().getName(), new LifestealAbility());
        abilityNames.put(new SpeedsterAbility().getName(), new SpeedsterAbility());
        abilityNames.put(new YeeterAbility().getName(), new YeeterAbility());

        abilityStatus.put(new AlchemistAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.alchemistConfig.disabled);
        abilityStatus.put(new BlindingAbility(),EldritchMobsMod.ELDRITCH_MOBS_CONFIG.blindingConfig.disabled);
        abilityStatus.put(new BurningAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.burningConfig.disabled);
        abilityStatus.put(new DrainingAbility(),EldritchMobsMod.ELDRITCH_MOBS_CONFIG.drainingConfig.disabled);
        abilityStatus.put(new DrowningAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.drowningConfig.disabled);
        abilityStatus.put(new DuplicatorAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.duplicatorConfig.disabled);
        abilityStatus.put(new GhastlyAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.ghastlyConfig.disabled);
        abilityStatus.put(new GravityAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.gravityConfig.disabled);
        abilityStatus.put(new LethargicAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.lethargicConfig.disabled);
        abilityStatus.put(new RustAbility(),  EldritchMobsMod.ELDRITCH_MOBS_CONFIG.rustConfig.disabled);
        abilityStatus.put(new StarvingAbility(),  EldritchMobsMod.ELDRITCH_MOBS_CONFIG.speedsterConfig.disabled);
        abilityStatus.put(new StormyAbility(),  EldritchMobsMod.ELDRITCH_MOBS_CONFIG.stormyConfig.disabled);
        abilityStatus.put(new WeaknessAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.weaknessConfig.disabled);
        abilityStatus.put(new WebslingingAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.webslingingConfig.disabled);
        abilityStatus.put(new CloakedAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.cloakedConfig.disabled);
        abilityStatus.put(new DeflectorAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.deflectorConfig.disabled);
        abilityStatus.put(new EnderAbility(),EldritchMobsMod.ELDRITCH_MOBS_CONFIG.enderConfig.disabled);
        abilityStatus.put(new UndyingAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.undyingConfig.disabled);
        abilityStatus.put(new RegeneratingAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.regeneratingConfig.disabled);
        abilityStatus.put(new ResistantAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.resistantConfig.disabled);
        abilityStatus.put(new SprinterAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.sprinterConfig.disabled);
        abilityStatus.put(new StarvingAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.starvingConfig.disabled);
        abilityStatus.put(new ThornyAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.thornyConfig.disabled);
        abilityStatus.put(new ToxicAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.toxicConfig.disabled);
        abilityStatus.put(new WitheringAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.witheringConfig.disabled);
        abilityStatus.put(new BerserkAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.berserkConfig.disabled);
        abilityStatus.put(new LifestealAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.lifestealConfig.disabled);
        abilityStatus.put(new SpeedsterAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.speedsterConfig.disabled);
        abilityStatus.put(new YeeterAbility(), EldritchMobsMod.ELDRITCH_MOBS_CONFIG.yeeterConfig.disabled);

    }



    public static final Random random = new Random();

    public static List<Ability> pickNRandom(List<Ability> lst, int n) {
        List<Ability> copy = new ArrayList<Ability>(lst);
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
    }

    public static List<Ability> pickNRandomForEntity(List<Ability> lst, int n, EntityType<?> entityType){
        List<Ability> copy = new ArrayList<Ability>(lst);
        copy.removeIf(ability -> abilityBlacklist.containsKey(ability.getName()) && abilityBlacklist.get(ability.getName()).contains(entityType));
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);

    }

    public static void addAbility(Ability ability){
        all_abilities.add(ability);
        abilityNames.put(ability.getName(), ability);
    }

    public static void addBlacklist(String name, List<EntityType<?>> entityTypeList) {
        abilityBlacklist.put(name,entityTypeList);
    }

    public static void removeAbility(Ability ability){
        all_abilities.removeIf(abilityElement -> abilityElement.getName().equals(ability.getName()));
    }

    public static void removeAbilityByName(String name){
        all_abilities.remove(abilityNames.get(name));
    }

    public static void removeDisabledAbilities(){
        for(Ability ability : abilityStatus.keySet()){
            if(abilityStatus.get(ability)){
                removeAbility(ability);
            }
        }
    }

}
