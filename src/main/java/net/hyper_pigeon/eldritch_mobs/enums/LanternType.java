package net.hyper_pigeon.eldritch_mobs.enums;

import com.google.common.collect.ImmutableSet;
import net.hyper_pigeon.eldritch_mobs.persistent_state.EffectBlockPersistentState;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public enum LanternType {
    SOOTHING         ("Soothing"        , MobRank.NONE    , EldritchMobsBlocks.SOOTHING_LANTERN),
    CURSING_ELITE    ("Elite Cursing"   , MobRank.ELITE   , Blocks.AIR),  // FIXME: Add Cursing Lanterns
    CURSING_ULTRA    ("Ultra Cursing"   , MobRank.ULTRA   , Blocks.AIR),
    CURSING_ELDRITCH ("Eldritch Cursing", MobRank.ELDRITCH, Blocks.AIR);

    public static final ImmutableSet<LanternType> LANTERN_TYPES = ImmutableSet.copyOf(LanternType.values());

    private final String displayName;
    private final MobRank associatedRank;
    private final Block associatedBlock;

    LanternType(String displayName, MobRank associatedRank, Block associatedBlock) {
        this.displayName     = displayName    ;
        this.associatedRank  = associatedRank ;
        this.associatedBlock = associatedBlock;
    }

    public String  getDisplayName    () { return displayName    ; }
    public MobRank getAssociatedRank () { return associatedRank ; }
    public Block   getAssociatedBlock() { return associatedBlock; }

    public static @Nullable LanternType fromDisplayName    (String  displayName    ) { return fromPredicate(lanternType -> lanternType.displayName    .equals(displayName    )); }
    public static @Nullable LanternType fromAssociatedRank (MobRank associatedRank ) { return fromPredicate(lanternType -> lanternType.associatedRank .equals(associatedRank )); }
    public static @Nullable LanternType fromAssociatedBlock(Block   associatedBlock) { return fromPredicate(lanternType -> lanternType.associatedBlock.equals(associatedBlock)); }

    private static @Nullable LanternType fromPredicate(Predicate<LanternType> predicate) {
        return Arrays.stream(LanternType.values())
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}
