package net.hyper_pigeon.eldritch_mobs.persistent_state;

import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.PersistentState;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class EffectBlockPersistentState extends PersistentState {

    private final HashMap<BlockPos, ChunkPos> blockChunks = new HashMap<>();
    public final String key;
    public final Block block;

    public EffectBlockPersistentState(Block block) { this(getKey(block), block); }
    public EffectBlockPersistentState(String key, Block block) {
        super();
        this.key = key;
        this.block = block;
    }

    public static String getKey(Block block) { return "EffectBlockPersistentState_" + Registry.BLOCK.getId(block).toUnderscoreSeparatedString(); }

    public static EffectBlockPersistentState readNbt(NbtCompound nbt) {
        NbtCompound contentsCompoundTag = nbt.getCompound("contents");

        NbtString blockString = (NbtString) contentsCompoundTag.get("blockId");
        if (blockString == null) return null;
        Block block = Registry.BLOCK.get(Identifier.tryParse(blockString.asString()));
        if (block == Blocks.AIR) return null;

        EffectBlockPersistentState effectBlockPersistentState = new EffectBlockPersistentState(getKey(block), block);

        NbtCompound blockChunksCompoundTag = contentsCompoundTag.getCompound("blockChunks");
        for (String blockPosString : blockChunksCompoundTag.getKeys()) effectBlockPersistentState.blockChunks.put(
            BlockPos.fromLong(Long.parseLong(blockPosString)),
            new ChunkPos(blockChunksCompoundTag.getLong(blockPosString))
        );
        effectBlockPersistentState.markDirty();

        return effectBlockPersistentState;
    }

    @Override public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound contentsCompoundTag = new NbtCompound();

        contentsCompoundTag.putString("blockId", Registry.BLOCK.getId(block).toString());

        NbtCompound blockChunksCompoundTag = new NbtCompound();
        blockChunks.forEach((blockPos, chunkPos) -> blockChunksCompoundTag.putLong(Long.toString(blockPos.asLong()), chunkPos.toLong()));
        contentsCompoundTag.put("blockChunks", blockChunksCompoundTag);

        nbt.put("contents", contentsCompoundTag);
        return nbt;
    }

    public static Function<Block, EffectBlockPersistentState> getFactory(ServerWorld world) {
        return block -> EffectBlockPersistentState.get(world, block);
    }
    public static EffectBlockPersistentState get(ServerWorld world, Block block) {
        return world.getPersistentStateManager().getOrCreate(
                EffectBlockPersistentState::readNbt,
                () -> new EffectBlockPersistentState(block),
                getKey(block)
        );
    }

    public void addChunkPos(ServerWorld world, BlockPos pos) {
        blockChunks.put(pos, world.getChunk(pos).getPos());
        this.markDirty();
    }

    public void removeChunkPos(BlockPos pos) {
        if (blockChunks.containsKey(pos)) {
            blockChunks.remove(pos);
            this.markDirty();
        }
    }

    public boolean containsChunkPos(BlockPos pos) {
        return blockChunks.containsKey(pos);
    }

    public boolean containsChunkPos(ChunkPos chunkPos) {
        return blockChunks.containsValue(chunkPos);
    }

    public int size() {
        return blockChunks.size();
    }

    public void printChunks() { printChunks(EldritchMobsMod.LOGGER::info); }
    public void printChunks(Consumer<String> printFunction) {
        blockChunks.forEach((pos, chunkPos) -> printFunction.accept(pos.toString() + " " + chunkPos.toString()));
    }

    public List<BlockPos> getBlocksInChunkPos(ChunkPos chunkPos) {
        return blockChunks.entrySet().stream()
                .filter(entry -> entry.getValue().equals(chunkPos))
                .map(Map.Entry::getKey)
                .toList();
    }
}
