package net.hyper_pigeon.eldritch_mobs.enums;

import com.google.common.collect.ImmutableSet;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobsBlocks;
import net.hyper_pigeon.eldritch_mobs.util.EldritchMobsUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Predicate;

public enum LanternType implements NamedEnum {
    NONE             ("None",             MobRank.NONE    , Blocks.AIR),
    SOOTHING         ("Soothing"        , MobRank.NONE    , EldritchMobsBlocks.SOOTHING_LANTERN),
    CURSING_ELITE    ("Elite Cursing"   , MobRank.ELITE   , Blocks.AIR),  // FIXME: Add Cursing Lanterns blocks
    CURSING_ULTRA    ("Ultra Cursing"   , MobRank.ULTRA   , Blocks.AIR),
    CURSING_ELDRITCH ("Eldritch Cursing", MobRank.ELDRITCH, Blocks.AIR);

    public static final ImmutableSet<LanternType> LANTERN_TYPES = ImmutableSet.copyOf(Arrays.stream(LanternType.values())
            .filter(Predicate.not(NONE::equals))
            .toList()
    );

    private final String   displayName     ;
    private final MobRank associatedMobRank;
    private final Block   associatedBlock  ;

    LanternType(String displayName, MobRank associatedMobRank, Block associatedBlock) {
        this.displayName       = displayName      ;
        this.associatedMobRank = associatedMobRank;
        this.associatedBlock   = associatedBlock  ;
    }

    @Override public String getDisplayName() { return displayName; }

    public MobRank getAssociatedMobRank() { return associatedMobRank; }
    public Block   getAssociatedBlock  () { return associatedBlock  ; }

    @Nullable public static LanternType fromAssociatedRank (MobRank associatedRank ) {
        return EldritchMobsUtils.enumFromPredicate(LanternType.class, lanternType -> lanternType.associatedMobRank.equals(associatedRank));
    }
    @Nullable public static LanternType fromAssociatedBlock(Block associatedBlock) {
        return EldritchMobsUtils.enumFromPredicate(LanternType.class, lanternType -> lanternType.associatedBlock.equals(associatedBlock));
    }

    private static @Nullable LanternType fromPredicate(Predicate<LanternType> predicate) {
        return Arrays.stream(LanternType.values())
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}
