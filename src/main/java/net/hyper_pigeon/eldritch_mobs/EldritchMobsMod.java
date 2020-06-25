package net.hyper_pigeon.eldritch_mobs;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.ComponentProvider;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.hyper_pigeon.eldritch_mobs.commands.SummonEldritchCommand;
import net.hyper_pigeon.eldritch_mobs.commands.SummonEliteCommand;
import net.hyper_pigeon.eldritch_mobs.commands.SummonUltraCommand;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.hyper_pigeon.eldritch_mobs.mod_components.modifiers.ModifierComponent;
import net.minecraft.command.arguments.EntitySummonArgumentType;
import net.minecraft.command.arguments.NbtCompoundTagArgumentType;
import net.minecraft.command.arguments.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EldritchMobsMod implements ModInitializer {

	public static final ComponentType<ModifierInterface> ELDRITCH_MODIFIERS =
			ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("eldritch_mobs:eldritch_modifiers"), ModifierInterface.class)
			.attach(EntityComponentCallback.event(LivingEntity.class), ModifierComponent::new);


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
