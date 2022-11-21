package net.hyper_pigeon.eldritch_mobs.enums;

import com.google.common.collect.ImmutableSet;
import net.hyper_pigeon.eldritch_mobs.util.EldritchMobsUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Predicate;

public enum MobRank implements NamedEnum {
    UNDECIDED("Undecided", LanternType.NONE),
    NONE     ("None"     , LanternType.NONE),
    ELITE    ("Elite"    , LanternType.CURSING_ELITE),
    ULTRA    ("Ultra"    , LanternType.CURSING_ULTRA),
    ELDRITCH ("Eldritch" , LanternType.CURSING_ELDRITCH);

    private final String displayName;
    private final LanternType associatedLanternType;

    MobRank(String displayName, LanternType associatedLanternType) {
        this.displayName = displayName;
        this.associatedLanternType = associatedLanternType;
    }

    public static @Nullable MobRank fromAssociatedLanternType(LanternType associatedLanternType) {
        return EldritchMobsUtils.enumFromPredicate(MobRank.class, mobRank -> mobRank.associatedLanternType.equals(associatedLanternType));
    }

    @Override public String getDisplayName() { return displayName; }

    public LanternType getAssociatedLanternType() { return associatedLanternType; }
}
