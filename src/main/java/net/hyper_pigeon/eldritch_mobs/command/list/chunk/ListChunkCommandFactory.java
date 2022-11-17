package net.hyper_pigeon.eldritch_mobs.command.list.chunk;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.hyper_pigeon.eldritch_mobs.enums.LanternType;
import net.hyper_pigeon.eldritch_mobs.persistent_state.EffectBlockPersistentState;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.SpreadPlayersCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ListChunkCommandFactory {

    protected static LiteralArgumentBuilder<ServerCommandSource> generate(@NotNull LanternType lanternType) {
        return CommandManager.literal(switch (lanternType) {
            case SOOTHING -> "soothing";
            case CURSING_ELITE, CURSING_ULTRA, CURSING_ELDRITCH -> "cursing";
        }).executes(context -> execute(context.getSource(), lanternType));
    }

    private static int execute(ServerCommandSource source, LanternType lanternType) {

        ServerPlayerEntity player = source.getPlayer();

        if (player == null) return 0;

        List<BlockPos> blocks = EffectBlockPersistentState.get(source.getWorld(), lanternType.getAssociatedBlock()).getBlocksInChunkPos(player.getChunkPos());

        if (blocks.isEmpty()) {
            player.sendMessage(Text.literal("No ").append(lanternType.getDisplayName()).append(" lanterns found in chunk."));
            return 1;
        }

        MutableText text = Text.literal("Found ")
                .append(String.valueOf(blocks.size()))
                .append(" ")
                .append(lanternType.getDisplayName())
                .append(" lanterns in chunk:\n");

        // Effectively final variable bruh moment.
        AtomicInteger idx = new AtomicInteger(0);
        do {
            BlockPos blockPos = blocks.get(idx.get());

            text.append("#").append(String.valueOf(idx.get() + 1))
                    .append(" | ")
                    .append(Text.literal("[").append(blockPos.toShortString()).append("]")
                            .styled(style -> source.hasPermissionLevel(2)
                                    ? style.withColor(Formatting.AQUA)
                                            .withClickEvent(new ClickEvent(
                                                    ClickEvent.Action.SUGGEST_COMMAND,
                                                    "/tp " + blockPos.getX() + " " + (blockPos.getY() + 0.5) + " " + blockPos.getZ()
                                            )).withHoverEvent(new HoverEvent(
                                                    HoverEvent.Action.SHOW_TEXT,
                                                    Text.literal("Teleport to block")
                                            ))
                                    : style
                            )
                    )
                    .append("\n");
        } while (idx.incrementAndGet() < blocks.size());

        player.sendMessage(text);

        return 1;
    }
}
