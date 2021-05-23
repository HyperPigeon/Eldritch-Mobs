package net.hyper_pigeon.eldritch_mobs.item;

import net.hyper_pigeon.eldritch_mobs.persistent_state.SoothingLanternPersistentState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.HashMap;
import java.util.Iterator;

public class SoothingLantern extends Block {

    public SoothingLantern(Settings settings) {
        super(settings);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if(!world.isClient()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            ((ServerWorld) world).getPersistentStateManager().getOrCreate(() ->
                    new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo")
                    .addChunkPos(chunkPos, pos);
//            System.out.println(((ServerWorld) world).getPersistentStateManager().getOrCreate(() ->
//                    new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo").getSize());
        }
    }

    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if(!world.isClient()) {
            ((ServerWorld) world).getPersistentStateManager().getOrCreate(() ->
                    new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo")
                    .removeChunkPos(pos);
//            System.out.println(((ServerWorld) world).getPersistentStateManager().getOrCreate(() ->
//                    new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo").getSize());
        }
    }


//    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
////        if(!world.isClient()) {
////            ((ServerWorld) world).getPersistentStateManager().getOrCreate(() ->
////                    new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo")
////                    .removeChunkPos(pos);
////            System.out.println(((ServerWorld) world).getPersistentStateManager().getOrCreate(() ->
////                    new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo").getSize());
////        }
//        System.out.println("check");
//        ((ServerWorld) world).getPersistentStateManager().getOrCreate(() ->
//                new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo")
//                .removeChunkPos(pos);
//        System.out.println(((ServerWorld) world).getPersistentStateManager().getOrCreate(() ->
//                new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo").getSize());
//        super.afterBreak(world, player, pos, state, blockEntity, stack);
//    }

//    public static int getSize(){
//        return ((ServerWorld) world).getPersistentStateManager().getOrCreate(() ->
//                new SoothingLanternPersistentState("LampChunkInfo"), "LampChunkInfo")
//                .getSize();
//    }
//
//    public static boolean containsChunk(BlockPos blockPos){
//        ChunkPos chunkPos = new ChunkPos(blockPos);
//        return chunksPlacedData.containsChunk(chunkPos);
//    }



}
