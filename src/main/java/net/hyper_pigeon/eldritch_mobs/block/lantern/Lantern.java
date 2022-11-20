package net.hyper_pigeon.eldritch_mobs.block.lantern;

import eu.pb4.polymer.api.block.PolymerHeadBlock;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.block.NamedBlock;
import net.hyper_pigeon.eldritch_mobs.persistent_state.EffectBlockPersistentState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class Lantern extends Block implements PolymerHeadBlock, NamedBlock {

    protected static final BooleanProperty LIT = BooleanProperty.of("lit");

    public final String inactiveSkin;
    public final String activeSkin;
    public final String name;
    public final DefaultParticleType[] particleTypes;

    public final Identifier id;

    public Lantern(
            Settings settings,
            String inactiveSkin,
            String activeSkin,
            boolean isActiveByDefault,
            String name,
            DefaultParticleType[] particleTypes
    ) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, isActiveByDefault));

        this.name = name;
        this.id = EldritchMobsMod.id(name);

        this.inactiveSkin = inactiveSkin;
        this.activeSkin = activeSkin;
        this.particleTypes = particleTypes;
    }

    @Override public String getBlockName() { return this.name; }

    // POLYMER | CLIENT

    @Override public Block getPolymerBlock(final BlockState state) {
        return getPolymerBlock();
    }
    @Override public String getPolymerSkinValue(final BlockState state) {
        return state.get(LIT) ? activeSkin : inactiveSkin;
    }

    // SERVERSIDE

    @Override public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            EffectBlockPersistentState persistentState = EffectBlockPersistentState.get((ServerWorld) world);
            if (state.get(LIT)) persistentState.addChunkPos(id, pos);
            else persistentState.removeChunkPos(id, pos);
        }
    }

    @Override public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
        if (world instanceof ServerWorldAccess serverWorldAccess)
            EffectBlockPersistentState.get(serverWorldAccess.toServerWorld()).removeChunkPos(id, pos);
    }

    @SuppressWarnings("deprecation")
    @Override public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(LIT)) world.spawnParticles(
                particleTypes[random.nextInt(particleTypes.length)],
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                1,
                0.0, 0.0, 0.0,
                0.0
        );
    }

    @Nullable @Override public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(LIT, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @SuppressWarnings("deprecation")
    @Override public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {

        if (!world.isClient) {
            boolean isLit = state.get(LIT);
            if (isLit != world.isReceivingRedstonePower(pos)) {
                // If the block is lit and is not receiving redstone power, turn it off.
                if (isLit) world.createAndScheduleBlockTick(pos, this, 4);
                // If the block is not lit and is receiving redstone power, turn it on.
                // Also add the chunk to the persistent state.
                else {
                    world.setBlockState(pos, state.cycle(LIT), 2);
                    EffectBlockPersistentState.get((ServerWorld) world).addChunkPos(id, pos);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        // Cycle the lit property to false if the block is lit, but we are not receiving redstone power.
        // Also remove the chunk from the persistent state.
        if (state.get(LIT) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(LIT), 2);
            EffectBlockPersistentState.get(world).removeChunkPos(id, pos);
        }
    }

    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
