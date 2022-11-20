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
import net.hyper_pigeon.eldritch_mobs.enums.MobRank;
import net.hyper_pigeon.eldritch_mobs.register.*;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;

public class EldritchMobsMod implements ModInitializer {

    public static final String MOD_NAME = "Eldritch Mobs";
    public static final String MOD_ID = "eldritch_mobs";
    public static Identifier id(String path) { return new Identifier(MOD_ID, path); }

    /**
     * This logger is used to write text to the console and the log file.
     * <p>
     * It is considered best practice to use your mod id/name as the logger's name, such that it's clear which mod wrote info, warnings, and errors.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    /**
     * This is the config instance. It is used to access the config values.
     */
    public static final EldritchMobsConfig ELDRITCH_MOBS_CONFIG = AutoConfig.register(EldritchMobsConfig.class, JanksonConfigSerializer::new).getConfig();

    public static final ComponentKey<ModifierComponent> ELDRITCH_MODIFIERS =
            ComponentRegistry.getOrCreate(id("eldritch_modifiers"), ModifierComponent.class);

    /**
     * This is the entry point for the mod; it is called by Fabric when the mod is first loaded.
     * <p>
     * This code runs as soon as Minecraft is in a mod-load-ready state, however, some things (like resources) may still be uninitialized.
     * <p>
     * Proceed with mild caution!
     */
    @Override
    public void onInitialize() {

        Instant instant = Instant.now();
        LOGGER.info("Channelling eldritch energies...");

        EldritchMobsEventListeners.init();
        EldritchMobsDataRegistry.init();
        EldritchMobsAbilities.init();
        EldritchMobsBlocks.init();
        EldritchMobsArgumentTypes.init();
        EldritchMobsCommands.init();

        LOGGER.info("Eldritch Mobs initialized in " + (Instant.now().toEpochMilli() - instant.toEpochMilli()) + "ms!");
    }

    public static List<Ability> getModifiers(ComponentProvider componentProvider) {
        return ELDRITCH_MODIFIERS.get(componentProvider).getModifiers();
    }

    public static MobRank getRank(ComponentProvider componentProvider) {
        return ELDRITCH_MODIFIERS.get(componentProvider).getRank();
    }
}
