package net.hyper_pigeon.eldritch_mobs.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public abstract class EldritchMobsUtils {

    public static <I> List<I> pickNRandom(Collection<I> list, int n, Predicate<I> removalPredicate) {
        List<I> copy = new ArrayList<>(list);
        copy.removeIf(removalPredicate);
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
    }
}
