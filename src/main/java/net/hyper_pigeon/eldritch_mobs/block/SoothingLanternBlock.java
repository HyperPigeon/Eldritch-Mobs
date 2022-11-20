package net.hyper_pigeon.eldritch_mobs.block;

import net.hyper_pigeon.eldritch_mobs.block.lantern.Lantern;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;

public class SoothingLanternBlock extends Lantern {

    public SoothingLanternBlock(final Settings settings, String name) {
        super(
                settings,
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2UzNWNhODY0N2MxZjJiYmU0NGU0ZmM4NDYxZDU1YjlmMDJiMjFmN2Y5YWJlM2JjNmFkZDk4MjgwMjk5NmJmOSJ9fX0=",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjI0MzcwNjA3NWE0ZjVmMjczMWE4YWQ1ZjYzNjA1OGIxZDViM2E0OWMxZjE0ZjViOWJhYmNmMjA0NGY1OTM1NSJ9fX0=",
                false,
                name,
                new DefaultParticleType[] { ParticleTypes.ENCHANT, ParticleTypes.PORTAL, ParticleTypes.EFFECT }
        );
    }
}
