package net.hyper_pigeon.eldritch_mobs.register;

import com.google.common.collect.HashMultimap;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.active.defensive.SprinterAbility;
import net.hyper_pigeon.eldritch_mobs.ability.active.offensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.defensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.offensive.*;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.Predicate;

import static net.hyper_pigeon.eldritch_mobs.register.EldritchMobsRegistries.ABILITY_REGISTRY;
import static net.hyper_pigeon.eldritch_mobs.util.EldritchMobsUtils.pickNRandom;

public abstract class EldritchMobsAbilities {

    private static final ArrayList<Ability> ALL_ABILITIES = new ArrayList<>(List.of(
            // Active | Defensive
            new SprinterAbility(),

            // Active | Offensive
            new AlchemistAbility(),
            new BlindingAbility(),
            new BurningAbility(),
            new DrainingAbility(),
            new DrowningAbility(),
            new DuplicatorAbility(),
            new GhastlyAbility(),
            new GravityAbility(),
            new LethargicAbility(),
            new StarvingAbility(),
            new StormyAbility(),
            new WeaknessAbility(),
            new WebslingingAbility(),

            // Passive | Defensive
            new CloakedAbility(),
            new DeflectorAbility(),
            new EnderAbility(),
            new RegeneratingAbility(),
            new ResistantAbility(),
            new ThornyAbility(),
            new ToxicAbility(),
            new UndyingAbility(),
            new WitheringAbility(),

            // Passive | Offensive
            new BerserkAbility(),
            new LifestealAbility(),
            new RustAbility(),
            new SpeedsterAbility(),
            new YeeterAbility()
    ));

    public static final ArrayList<Ability> ABILITY_REGISTER_BLACKLIST = new ArrayList<>();
    public static final HashMultimap<Ability, EntityType<?>> ABILITY_ENTITY_TYPE_BLACKLIST = HashMultimap.create();

    public static Optional<Ability> getAbilityByName(String name) {
        return ABILITY_REGISTRY.getOrEmpty(Ability.getIdentifier(name));
    }

    public static void blacklistEntityType(Ability ability, EntityType<?> ...entityTypes) {
        ABILITY_ENTITY_TYPE_BLACKLIST.putAll(ability, Arrays.stream(entityTypes).toList());
    }

    public static void whitelistEntityType(Ability ability, EntityType<?> ...entityTypes) {
        Arrays.stream(entityTypes).forEach(t -> ABILITY_ENTITY_TYPE_BLACKLIST.remove(ability, t));
    }

    public static void init() {
        ALL_ABILITIES.stream()
                .filter(Predicate.not(ABILITY_REGISTER_BLACKLIST::contains))
                .forEach(ability -> Registry.register(
                        ABILITY_REGISTRY,
                        ability.getIdentifier(),
                        ability
                ));
    }

    public static List<Ability> pickNRandomForEntity(int n, EntityType<?> entityType) {
        return pickNRandom(
                ALL_ABILITIES,
                n,
                ability -> ABILITY_ENTITY_TYPE_BLACKLIST.containsKey(ability)
                        && ABILITY_ENTITY_TYPE_BLACKLIST.get(ability).contains(entityType)
        );
    }
}
