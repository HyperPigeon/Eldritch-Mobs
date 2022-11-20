package net.hyper_pigeon.eldritch_mobs.persistent_state;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtLongArray;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.PersistentState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EffectBlockPersistentState extends PersistentState {

    private static final String KEY = "EffectBlockPersistentState";

    SetMultimap<Identifier, BlockPos> blockPosMap = MultimapBuilder.hashKeys().hashSetValues().build();

    public static EffectBlockPersistentState readNbt(NbtCompound nbt) {

        EffectBlockPersistentState effectBlockPersistentState = new EffectBlockPersistentState();

        nbt.getKeys().forEach(key -> {
            effectBlockPersistentState.blockPosMap.putAll(new Identifier(key), Arrays.stream(
                    nbt.getLongArray(key)).collect(ArrayList::new,
                    (list, l) -> list.add(BlockPos.fromLong(l)),
                    ArrayList::addAll)
            );
        });
        effectBlockPersistentState.markDirty();

        return effectBlockPersistentState;
    }

    @Override public NbtCompound writeNbt(NbtCompound nbt) {

        blockPosMap.keys().forEach(blockIdentifier -> nbt.put(
                blockIdentifier.toString(),
                new NbtLongArray(blockPosMap.get(blockIdentifier).stream()
                        .mapToLong(BlockPos::asLong)
                        .toArray()
                )
        ));

        return nbt;
    }

    public static EffectBlockPersistentState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(EffectBlockPersistentState::readNbt, EffectBlockPersistentState::new, KEY);
    }

    public void addChunkPos(Identifier blockIdentifier, BlockPos pos) {
        blockPosMap.put(blockIdentifier, pos);
        this.markDirty();
    }

    public void removeChunkPos(Identifier blockIdentifier, BlockPos pos) {
        if (blockPosMap.containsEntry(blockIdentifier, pos)) {
            blockPosMap.remove(blockIdentifier, pos);
            this.markDirty();
        }
    }

    public boolean containsChunkPos(Identifier blockIdentifier, ChunkPos pos) {
        return blockPosMap.get(blockIdentifier).stream()
                .anyMatch(blockPos -> ChunkSectionPos.getSectionCoord(blockPos.getX()) == pos.x && ChunkSectionPos.getSectionCoord(blockPos.getZ()) == pos.z);
    }

    public int blocksCount() {
        return blockPosMap.size();
    }

    public int countOfPositionsForBlock(Identifier blockIdentifier) {
        return blockPosMap.get(blockIdentifier).size();
    }

    public void printChunks() { printChunks(EldritchMobsMod.LOGGER::info); }
    public void printChunks(Consumer<String> printFunction) {
        blockPosMap.asMap().forEach((blockIdentifier, blockPosSet) ->
                printFunction.accept(blockIdentifier + " | " + blockPosSet.toString())
        );
    }

    public List<BlockPos> getBlocksInChunkPos(Identifier blockIdentifier, ChunkPos chunkPos) {
        return blockPosMap.get(blockIdentifier).stream()
                .filter(blockPos -> ChunkSectionPos.getSectionCoord(blockPos.getX()) == chunkPos.x && ChunkSectionPos.getSectionCoord(blockPos.getZ()) == chunkPos.z)
                .toList();
    }
}
