package net.hyper_pigeon.eldritch_mobs.command.argument_type;

import com.mojang.brigadier.context.CommandContext;
import net.hyper_pigeon.eldritch_mobs.enums.LanternType;

import java.util.HashSet;
import java.util.List;

public class LanternArgumentType extends EnumArgumentType<LanternType> {

    private LanternArgumentType() {
        super(LanternType.class, List.of(LanternType.NONE));
    }

    public static LanternArgumentType lanternType() { return new LanternArgumentType(); }

    public static <S> LanternType getLanternType(CommandContext<S> context, String name) {
        return EnumArgumentType.get(context, name, LanternType.class);
    }
}
