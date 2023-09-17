package net.hyper_pigeon.eldritch_mobs.block;


import eu.pb4.polymer.core.api.block.PolymerHeadBlock;
import net.hyper_pigeon.eldritch_mobs.persistent_state.SoothingLanternPersistentState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SoothingLanternBlock extends Block implements PolymerHeadBlock {

    public static final BooleanProperty LIT = Properties.LIT;

    @SuppressWarnings("SpellCheckingInspection") public static final String INACTIVE_SKIN
            = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2UzNWNhODY0N2MxZjJiYmU0NGU0ZmM4NDYxZDU1YjlmMDJiMjFmN2Y5YWJlM2JjNmFkZDk4MjgwMjk5NmJmOSJ9fX0=";
    @SuppressWarnings("SpellCheckingInspection") public static final String ACTIVE_SKIN
            = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjI0MzcwNjA3NWE0ZjVmMjczMWE4YWQ1ZjYzNjA1OGIxZDViM2E0OWMxZjE0ZjViOWJhYmNmMjA0NGY1OTM1NSJ9fX0=";

    public static final DefaultParticleType[] PARTICLE_TYPES = new DefaultParticleType[] { ParticleTypes.ENCHANT, ParticleTypes.PORTAL, ParticleTypes.EFFECT };

    public SoothingLanternBlock(final Settings settings) {
        this(settings, false);
    }

    public SoothingLanternBlock(final Settings settings, final boolean isActive) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, isActive));
    }

    // POLYMER | CLIENT

    @Override public Block getPolymerBlock(final BlockState state) {
        return getPolymerBlock();
    }

    @Override
    public String getPolymerSkinValue(BlockState state, BlockPos pos, ServerPlayerEntity player) {
        return state.get(LIT) ? ACTIVE_SKIN : INACTIVE_SKIN;
    }

    // SERVERSIDE

    @Override public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            if (state.get(LIT)) SoothingLanternPersistentState.get((ServerWorld) world).addChunkPos((ServerWorld) world, pos);
            else SoothingLanternPersistentState.get((ServerWorld) world).removeChunkPos(pos);
        }
    }

    @Override public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
        if (world instanceof ServerWorldAccess serverWorldAccess) SoothingLanternPersistentState.get(serverWorldAccess.toServerWorld()).removeChunkPos(pos);
    }

    @SuppressWarnings("deprecation")
    @Override public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(LIT)) world.spawnParticles(
                PARTICLE_TYPES[random.nextInt(PARTICLE_TYPES.length)],
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                1,
                0.0,
                0.0,
                0.0,
                0.0
        );
    }

    @Nullable @Override public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(LIT, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @SuppressWarnings("deprecation")
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {

        SoothingLanternPersistentState.get((ServerWorld) world).printSoothingLanternChunks();

        if (!world.isClient) {
            boolean isLit = state.get(LIT);
            if (isLit != world.isReceivingRedstonePower(pos)) {
                // If the block is lit and is not receiving redstone power, turn it off.
                if (isLit) world.scheduleBlockTick(pos, this, 4);
                // If the block is not lit and is receiving redstone power, turn it on.
                // Also add the chunk to the persistent state.
                else {
                    world.setBlockState(pos, state.cycle(LIT), 2);
                    SoothingLanternPersistentState.get((ServerWorld) world).addChunkPos((ServerWorld) world, pos);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        // Cycle the lit property to false if the block is lit, but we are not receiving redstone power.
        // Also remove the chunk from the persistent state.
        if (state.get(LIT) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(LIT), 2);
            SoothingLanternPersistentState.get(world).removeChunkPos(pos);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }


}
