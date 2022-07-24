package net.hyper_pigeon.eldritch_mobs.block;

import net.hyper_pigeon.eldritch_mobs.persistent_state.SoothingLanternPersistentState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SoothingLanternBlock extends Block {
    public SoothingLanternBlock(Settings settings) {
        super(settings);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if(!world.isClient()) {
            ChunkPos chunkPos = new ChunkPos(pos);
            SoothingLanternPersistentState.get((ServerWorld) world).addChunkPos(chunkPos, pos);
        }
    }

    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if(!world.isClient()) {
            SoothingLanternPersistentState.get((ServerWorld) world).removeChunkPos(pos);
        }
    }
}
