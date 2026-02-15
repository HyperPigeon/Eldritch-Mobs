package net.hyper_pigeon.eldritch_mobs.register;


import eu.pb4.polymer.core.api.block.PolymerHeadBlock;
import eu.pb4.polymer.core.api.item.PolymerHeadBlockItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.block.SoothingLanternBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;

import static net.hyper_pigeon.eldritch_mobs.EldritchMobsMod.id;

public class EldritchMobsBlocks {

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(final int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    public static final SoothingLanternBlock SOOTHING_LANTERN = new SoothingLanternBlock(
            AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(EldritchMobsMod.MOD_ID, "soothing_lantern")))
                    .requiresTool()
                    .strength(1.5F)
                    .sounds(BlockSoundGroup.METAL)
                    .luminance(createLightLevelFromLitBlockState(10))
                    .allowsSpawning((state, world, pos, type) -> false)
                    .ticksRandomly()
                    .nonOpaque()
    );



    public static void init() {
        registerHeadBlock("soothing_lantern", SOOTHING_LANTERN, new Item.Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(EldritchMobsMod.MOD_ID, "soothing_lantern")))
                .maxCount(1));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) -> itemGroup.add(SOOTHING_LANTERN));
    }

    private static <T extends Block & PolymerHeadBlock> void registerHeadBlock(final String name, final T block, final Item.Settings settings) {
        Identifier id = id(name);
        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new PolymerHeadBlockItem(block, settings));
    }
}
