package net.hyper_pigeon.eldritch_mobs.register;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.hyper_pigeon.eldritch_mobs.ability.data.AbilityBlacklistManager;
import net.hyper_pigeon.eldritch_mobs.ability.data.CustomAbilityManager;
import net.minecraft.resource.ResourceType;

public class EldritchMobsDataRegistry {
    public static void init() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new CustomAbilityManager());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new AbilityBlacklistManager());
    }
}
