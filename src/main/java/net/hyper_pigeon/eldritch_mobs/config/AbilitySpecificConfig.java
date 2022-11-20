package net.hyper_pigeon.eldritch_mobs.config;

public abstract class AbilitySpecificConfig {
    public final String name;
    public final boolean disabled;

    public AbilitySpecificConfig(String name, boolean disabled) {
        this.name = name;
        this.disabled = disabled;
    }
}
