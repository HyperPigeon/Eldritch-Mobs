package net.hyper_pigeon.eldritch_mobs.command.argument_type;

import com.mojang.brigadier.context.CommandContext;
import net.hyper_pigeon.eldritch_mobs.enums.MobRank;

import java.util.List;

public class MobRankArgumentType extends EnumArgumentType<MobRank> {

    private MobRankArgumentType() {
        super(MobRank.class, List.of(MobRank.NONE, MobRank.UNDECIDED));
    }

    public static MobRankArgumentType mobRank() { return new MobRankArgumentType(); }

    public static <S> MobRank getMobRank(CommandContext<S> context, String name) {
        return EnumArgumentType.get(context, name, MobRank.class);
    }
}
