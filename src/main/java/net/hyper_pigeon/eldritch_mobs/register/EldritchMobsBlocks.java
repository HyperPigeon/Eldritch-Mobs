package net.hyper_pigeon.eldritch_mobs.register;

import net.hyper_pigeon.eldritch_mobs.block.SoothingLanternBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EldritchMobsBlocks {

    public static final SoothingLanternBlock SOOTHING_LANTERN = new SoothingLanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(1.5F).sounds(BlockSoundGroup.METAL).luminance((state) -> {
        return 10;
    }).nonOpaque());

    public static void init(){
        Registry.register(Registry.BLOCK, new Identifier("eldritch_mobs", "soothing_lantern"), SOOTHING_LANTERN);
        Registry.register(Registry.ITEM, new Identifier("eldritch_mobs", "soothing_lantern"), new BlockItem(SOOTHING_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }

}
