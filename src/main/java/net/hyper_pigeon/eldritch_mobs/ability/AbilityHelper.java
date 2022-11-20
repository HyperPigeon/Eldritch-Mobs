package net.hyper_pigeon.eldritch_mobs.ability;

import net.hyper_pigeon.eldritch_mobs.ability.active.defensive.SprinterAbility;
import net.hyper_pigeon.eldritch_mobs.ability.active.offensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.defensive.*;
import net.hyper_pigeon.eldritch_mobs.ability.passive.offensive.*;
import net.minecraft.entity.EntityType;

import java.util.*;
import java.util.stream.Collectors;

public class AbilityHelper {

    public static final ArrayList<Ability> ALL_ABILITIES = new ArrayList<>(List.of(
            new AlchemistAbility(),
            new BlindingAbility(),
            new BurningAbility(),
            new DrainingAbility(),
            new DrowningAbility(),
            new DuplicatorAbility(),
            new GhastlyAbility(),
            new GravityAbility(),
            new LethargicAbility(),
            new RustAbility(),
            new StarvingAbility(),
            new StormyAbility(),
            new WeaknessAbility(),
            new WebslingingAbility(),
            new CloakedAbility(),
            new DeflectorAbility(),
            new EnderAbility(),
            new UndyingAbility(),
            new RegeneratingAbility(),
            new ResistantAbility(),
            new SprinterAbility(),
            new StarvingAbility(),
            new ThornyAbility(),
            new ToxicAbility(),
            new WitheringAbility(),
            new BerserkAbility(),
            new LifestealAbility(),
            new SpeedsterAbility(),
            new YeeterAbility()
    ));

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

    public static void blackListEntityType(String name, EntityType<?> ...entityTypes) { ABILITY_BLACKLIST.put(name, Arrays.asList(entityTypes)); }

    public static List<String> getAbilityNames() {
        return ALL_ABILITIES.stream().map(Ability::getName).collect(Collectors.toList());
    }

    public static void removeDisabledAbilities() { ALL_ABILITIES.removeIf(Ability::getDisabled); }
}
