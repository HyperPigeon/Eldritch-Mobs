package net.hyper_pigeon.eldritch_mobs.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Iterator;

public class SoothingLantern extends Block {

    public static PersistentState chunksPlacedData = new PersistentState("LampChunkInfo");


    public SoothingLantern(Settings settings) {
        super(settings);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        ChunkPos chunkPos = new ChunkPos(pos);
        chunksPlacedData.addChunkPos(chunkPos,pos);
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world,  player, pos, state, blockEntity, stack);

        //ChunkPos chunkPos = new ChunkPos(pos);
        chunksPlacedData.removeChunkPos(pos);
    }

    public static boolean containsChunk(BlockPos blockPos){
        ChunkPos chunkPos = new ChunkPos(blockPos);
        return chunksPlacedData.containsChunk(chunkPos);
    }

    static class PersistentState extends net.minecraft.world.PersistentState {

        private HashMap<String, ChunkPos> map = new HashMap<>();
        //private static ArrayList<ChunkPos> chunks = new ArrayList();

        public PersistentState(String key) {
            super(key);
        }


        public void fromTag(CompoundTag tag) {
            CompoundTag compoundTag = tag.getCompound("contents");
            Iterator var3 = compoundTag.getKeys().iterator();

            while(var3.hasNext()) {
                String string = (String)var3.next();
                this.map.put(string, new ChunkPos(compoundTag.getLong(string)));
            }

        }

        public CompoundTag toTag(CompoundTag tag) {
            CompoundTag compoundTag = new CompoundTag();

            this.map.forEach((string, chunkPos) -> {
                compoundTag.putLong(string,chunkPos.toLong());
            });
            tag.put("contents", compoundTag);
            return tag;

        }

        public void addChunkPos(ChunkPos chunkPos, BlockPos blockPos){
            map.put(blockPos.toString(),chunkPos);
        }

        public void removeChunkPos(BlockPos pos){
            map.remove(pos.toString());
        }

        public boolean containsChunk(ChunkPos chunkPos){
            return map.containsValue(chunkPos);
        }
    }



}
