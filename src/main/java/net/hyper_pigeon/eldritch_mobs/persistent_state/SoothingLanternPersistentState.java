package net.hyper_pigeon.eldritch_mobs.persistent_state;

import com.mojang.serialization.Codec;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.HashMap;
import java.util.Map;

public class SoothingLanternPersistentState extends PersistentState {

    private final Map<String, ChunkPos> soothingLanternChunks = new HashMap<>();

    public SoothingLanternPersistentState() {}

    public static final Codec<SoothingLanternPersistentState> CODEC =
            Codec.unboundedMap(
                    Codec.STRING,
                    ChunkPos.CODEC
            ).xmap(
                    SoothingLanternPersistentState::new,
                    SoothingLanternPersistentState::getMap
            );

    public static final PersistentStateType<SoothingLanternPersistentState> TYPE =
            new PersistentStateType<>(
                    "SoothingLanternChunks",
                    SoothingLanternPersistentState::new,
                    CODEC,
                    null
            );

    public static SoothingLanternPersistentState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE);
    }


    public SoothingLanternPersistentState(Map<String, ChunkPos> map) {
        this.soothingLanternChunks.putAll(map);
    }

    public Map<String, ChunkPos> getMap() {
        return soothingLanternChunks;
    }

    public void addChunkPos(ServerWorld world, BlockPos pos) {
        soothingLanternChunks.put(pos.toString(), world.getChunk(pos).getPos());
        markDirty();
    }

    public void removeChunkPos(BlockPos pos) {
        if (soothingLanternChunks.remove(pos.toString()) != null) {
            markDirty();
        }
    }

    public boolean containsChunk(ChunkPos chunkPos) {
        return soothingLanternChunks.containsValue(chunkPos);
    }

    public void printSoothingLanternChunks() {
        for (String key : soothingLanternChunks.keySet()) EldritchMobsMod.LOGGER.info(soothingLanternChunks.get(key).toString());
    }
}
