package net.hyper_pigeon.eldritch_mobs.enums;

import net.hyper_pigeon.eldritch_mobs.persistent_state.EffectBlockPersistentState;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.List;

public enum LanternType {
    SOOTHING         ("Soothing"        , MobRank.NONE    , EldritchMobsBlocks.SOOTHING_LANTERN),
    CURSING_ELITE    ("Elite Cursing"   , MobRank.ELITE   , Blocks.AIR),  // FIXME: Add Cursing Lanterns
    CURSING_ULTRA    ("Ultra Cursing"   , MobRank.ULTRA   , Blocks.AIR),
    CURSING_ELDRITCH ("Eldritch Cursing", MobRank.ELDRITCH, Blocks.AIR);

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
}
