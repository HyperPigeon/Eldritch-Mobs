package net.hyper_pigeon.eldritch_mobs.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public String getModId() {
        return "eldritch_mobs";
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> (Screen) AutoConfig.getConfigScreen(EldritchMobsConfig.class, screen).get();
    }

}
