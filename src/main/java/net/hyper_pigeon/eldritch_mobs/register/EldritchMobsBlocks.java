package net.hyper_pigeon.eldritch_mobs.register;

import eu.pb4.polymer.api.block.PolymerHeadBlock;
import eu.pb4.polymer.api.item.PolymerHeadBlockItem;
import net.hyper_pigeon.eldritch_mobs.block.SoothingLanternBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.ToIntFunction;

import static net.hyper_pigeon.eldritch_mobs.EldritchMobsMod.id;

public class EldritchMobsBlocks {

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(final int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    public static final SoothingLanternBlock SOOTHING_LANTERN = new SoothingLanternBlock(
            AbstractBlock.Settings
                    .of(Material.METAL)
                    .requiresTool()
                    .strength(1.5F)
                    .sounds(BlockSoundGroup.METAL)
                    .luminance(createLightLevelFromLitBlockState(10))
                    .allowsSpawning((state, world, pos, type) -> false)
                    .ticksRandomly()
                    .nonOpaque()
    );

    public static void init() {
        registerHeadBlock("soothing_lantern", SOOTHING_LANTERN, new Item.Settings().group(ItemGroup.REDSTONE));
    }

    private static <T extends Block & PolymerHeadBlock> void registerHeadBlock(final String name, final T block, final Item.Settings settings) {
        Identifier id = id(name);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new PolymerHeadBlockItem(block, settings));
    }
}
