package net.hyper_pigeon.eldritch_mobs.persistent_state;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.HashMap;
import java.util.Iterator;

public class SoothingLanternPersistentState extends net.minecraft.world.PersistentState{
    private HashMap<String, ChunkPos> soothingLanternChunks = new HashMap<>();

    public SoothingLanternPersistentState(String key) {
        super(key);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        CompoundTag compoundTag = tag.getCompound("contents");
        Iterator var3 = compoundTag.getKeys().iterator();

        while(var3.hasNext()) {
            String string = (String)var3.next();
            this.soothingLanternChunks.put(string, new ChunkPos(compoundTag.getLong(string)));
        }
        this.markDirty();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        CompoundTag compoundTag = new CompoundTag();

        this.soothingLanternChunks.forEach((string, chunkPos) -> {
            compoundTag.putLong(string,chunkPos.toLong());
        });
        tag.put("contents", compoundTag);
        return tag;
    }

    public void addChunkPos(ChunkPos chunkPos, BlockPos blockPos){
        soothingLanternChunks.put(blockPos.toString(),chunkPos);
        this.markDirty();
    }

    public void removeChunkPos(BlockPos pos){
        if(soothingLanternChunks.containsKey(pos.toString())) {
            soothingLanternChunks.remove(pos.toString());
            this.markDirty();
        }

    }

    public boolean containsChunk(ChunkPos chunkPos){
        //System.out.println(chunkPos);
        return soothingLanternChunks.containsValue(chunkPos);
    }

    public int getSize(){
        return soothingLanternChunks.size();
    }

    public static SoothingLanternPersistentState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(() -> new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo");
    }

    public void printSoothingLanternChunks(){
        for (String blockPosString : soothingLanternChunks.keySet()){
            System.out.println(soothingLanternChunks.get(blockPosString));
        }
    }

    public ChunkPos getChunkPos(ChunkPos chunkPos){
        for (String blockPosString : soothingLanternChunks.keySet()){
            if(chunkPos.equals(soothingLanternChunks.get(blockPosString))){
                return chunkPos;
            }
        }
        return null;
    }

}
