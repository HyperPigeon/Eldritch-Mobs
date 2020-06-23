package net.hyper_pigeon.eldritch_mobs;

import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.ComponentProvider;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.hyper_pigeon.eldritch_mobs.mod_components.interfaces.ModifierInterface;
import net.hyper_pigeon.eldritch_mobs.mod_components.modifiers.ModifierComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class EldritchMobsMod implements ModInitializer {


	public static final ComponentType<ModifierInterface> ELDRITCH_MODIFIERS =
			ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("eldritch_mobs:eldritch_modifiers"), ModifierInterface.class)
			.attach(EntityComponentCallback.event(LivingEntity.class), entity -> new ModifierComponent());

//	public static final ComponentType<HealthInterface> HEALTH_MODIFIERS =
//			ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("eldritch_mobs:health_modifiers"), HealthInterface.class)
//					.attach(EntityComponentCallback.event(MobEntity.class), entity -> new HealthModifier(entity));



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

		System.out.println("Hello Fabric world!");
	}
}
