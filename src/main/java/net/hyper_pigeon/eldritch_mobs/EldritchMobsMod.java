package net.hyper_pigeon.eldritch_mobs;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.ComponentProvider;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.hyper_pigeon.eldritch_mobs.commands.SummonEldritchCommand;
import net.hyper_pigeon.eldritch_mobs.commands.SummonEliteCommand;
import net.hyper_pigeon.eldritch_mobs.commands.SummonUltraCommand;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.hyper_pigeon.eldritch_mobs.mod_components.modifiers.ModifierComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class EldritchMobsMod implements ModInitializer {

	public static final ComponentType<ModifierInterface> ELDRITCH_MODIFIERS =
			ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("eldritch_mobs:eldritch_modifiers"), ModifierInterface.class)
			.attach(EntityComponentCallback.event(LivingEntity.class), ModifierComponent::new);

	public static final EldritchMobsConfig CONFIG = AutoConfig.register(EldritchMobsConfig.class, JanksonConfigSerializer::new).getConfig();

	private static final Identifier ELDRITCH_BLACKLIST_ID = new Identifier("eldritch_mobs:eldritch_blacklist");
	public static final Tag<EntityType<?>> ELDRITCH_BLACKLIST = TagRegistry.entityType(ELDRITCH_BLACKLIST_ID);

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
	}



}
