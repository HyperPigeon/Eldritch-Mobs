package net.hyper_pigeon.eldritch_mobs;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.hyper_pigeon.eldritch_mobs.commands.SummonEldritchCommand;
import net.hyper_pigeon.eldritch_mobs.commands.SummonEliteCommand;
import net.hyper_pigeon.eldritch_mobs.commands.SummonUltraCommand;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.hyper_pigeon.eldritch_mobs.item.SoothingLantern;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.hyper_pigeon.eldritch_mobs.mod_components.modifiers.ModifierComponent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EldritchMobsMod implements ModInitializer {

	public static final ComponentKey<ModifierInterface> ELDRITCH_MODIFIERS =
			ComponentRegistry.getOrCreate(new Identifier("eldritch_mobs:eldritch_modifiers"), ModifierInterface.class);


	public static final EldritchMobsConfig CONFIG = AutoConfig.register(EldritchMobsConfig.class, JanksonConfigSerializer::new).getConfig();

	private static final Identifier ELDRITCH_BLACKLIST_ID = new Identifier("eldritch_mobs:eldritch_blacklist");
	public static final Tag<EntityType<?>> ELDRITCH_BLACKLIST = TagRegistry.entityType(ELDRITCH_BLACKLIST_ID);

	private static final Identifier ELDRITCH_ALLOWED_ID = new Identifier("eldritch_mobs:eldritch_allowed");
	public static final Tag<EntityType<?>> ELDRITCH_ALLOWED = TagRegistry.entityType(ELDRITCH_ALLOWED_ID);

	public static final Identifier UserDefinedEliteLootID = new Identifier("eldritch_mobs:entity/custom_elite_loot");
	public static final Identifier UserDefinedUltraLootID = new Identifier("eldritch_mobs:entity/custom_ultra_loot");
	public static final Identifier UserDefinedEldritchLootID = new Identifier("eldritch_mobs:entity/custom_eldritch_loot");

	public static final SoothingLantern SOOTHING_LANTERN = new SoothingLantern(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(1.5F).sounds(BlockSoundGroup.METAL).luminance((state) -> {
		return 10;
	}).nonOpaque());
	

	public static void useAbility(ComponentProvider provider) {
		ELDRITCH_MODIFIERS.get(provider).useAbility((MobEntity) provider);
	}

	public static void use_damageActivatedAbility(ComponentProvider provider, DamageSource source, float amount){
		ELDRITCH_MODIFIERS.get(provider).damageActivatedMod((LivingEntity) provider,source,amount);
	}

	public static void rank(ComponentProvider provider) {
		ELDRITCH_MODIFIERS.get(provider).setRank();
	}

	public static void mods(ComponentProvider provider) {
		ELDRITCH_MODIFIERS.get(provider).setMods();
	}

	public static boolean hasMod(ComponentProvider provider, String name) {
		return ELDRITCH_MODIFIERS.get(provider).hasMod(name);
	}

	public static boolean isElite(ComponentProvider provider){
		return ELDRITCH_MODIFIERS.get(provider).isElite();
	}

	public static boolean isUltra(ComponentProvider provider){
		return ELDRITCH_MODIFIERS.get(provider).isUltra();
	}

	public static boolean isEldritch(ComponentProvider provider){
		return ELDRITCH_MODIFIERS.get(provider).isEldritch();
	}

	public static String get_mod_list(ComponentProvider provider){
		return ELDRITCH_MODIFIERS.get(provider).get_mod_string();
	}


	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register(SummonEliteCommand::register);
		CommandRegistrationCallback.EVENT.register(SummonUltraCommand::register);
		CommandRegistrationCallback.EVENT.register(SummonEldritchCommand::register);
		Registry.register(Registry.BLOCK, new Identifier("eldritch_mobs", "soothing_lantern"), SOOTHING_LANTERN);
		Registry.register(Registry.ITEM, new Identifier("eldritch_mobs", "soothing_lantern"), new BlockItem(SOOTHING_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS)));
	}



}
