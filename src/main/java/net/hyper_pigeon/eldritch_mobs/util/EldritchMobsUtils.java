package net.hyper_pigeon.eldritch_mobs.util;

import net.hyper_pigeon.eldritch_mobs.enums.NamedEnum;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public abstract class EldritchMobsUtils {

    public static <I> List<I> pickNRandom(Collection<I> list, int n, Predicate<I> removalPredicate) {
        List<I> copy = new ArrayList<>(list);
        copy.removeIf(removalPredicate);
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
    }

    @Nullable public static <E extends Enum<E> & NamedEnum> E enumFromPredicate(Class<E> enumClass, Predicate<? super E> predicate) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}
