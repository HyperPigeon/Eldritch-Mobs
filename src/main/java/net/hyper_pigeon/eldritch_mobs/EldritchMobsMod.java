package net.hyper_pigeon.eldritch_mobs;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.hyper_pigeon.eldritch_mobs.ability.Ability;
import net.hyper_pigeon.eldritch_mobs.ability.AbilityHelper;
import net.hyper_pigeon.eldritch_mobs.component.interfaces.ModifierComponent;
import net.hyper_pigeon.eldritch_mobs.config.EldritchMobsConfig;
import net.hyper_pigeon.eldritch_mobs.rank.MobRank;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobsBlocks;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobsCommands;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobsDataRegistry;
import net.hyper_pigeon.eldritch_mobs.register.EldritchMobsEventListeners;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EldritchMobsMod implements ModInitializer {

    public static final String MOD_NAME = "Eldritch Mobs";
    public static final String MOD_ID = "eldritch_mobs";
    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final EldritchMobsConfig ELDRITCH_MOBS_CONFIG = AutoConfig.register(EldritchMobsConfig.class, JanksonConfigSerializer::new).getConfig();

    public static final ComponentKey<ModifierComponent> ELDRITCH_MODIFIERS =
            ComponentRegistry.getOrCreate(id("eldritch_modifiers"), ModifierComponent.class);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("Channelling eldritch energies...");

        EldritchMobsEventListeners.init();
        EldritchMobsCommands.init();
        EldritchMobsDataRegistry.init();
        EldritchMobsBlocks.init();
        AbilityHelper.removeDisabledAbilities();
    }

    public static List<Ability> getModifiers(ComponentProvider componentProvider) {
        return ELDRITCH_MODIFIERS.get(componentProvider).getModifiers();
    }

    public static MobRank getRank(ComponentProvider componentProvider) {
        return ELDRITCH_MODIFIERS.get(componentProvider).getRank();
    }
}
