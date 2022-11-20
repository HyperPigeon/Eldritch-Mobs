package net.hyper_pigeon.eldritch_mobs.command.commands.list.chunk;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.hyper_pigeon.eldritch_mobs.command.argument_type.LanternArgumentType;
import net.hyper_pigeon.eldritch_mobs.enums.LanternType;
import net.hyper_pigeon.eldritch_mobs.persistent_state.EffectBlockPersistentState;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ListChunkCommandRoot {

    public static final LiteralArgumentBuilder<ServerCommandSource> LIST_CHUNK_COMMAND_ROOT = CommandManager.literal("chunk").then(
            CommandManager.argument("lantern_type", LanternArgumentType.lanternType())
                    .executes(ListChunkCommandRoot::execute)
    );

    private static int execute(CommandContext<ServerCommandSource> context) {

        ServerCommandSource source = context.getSource();

        ServerPlayerEntity player = source.getPlayer();
        if (player == null) return 0;

        LanternType lanternType = LanternArgumentType.getLanternType(context, "lantern_type");

        List<BlockPos> blocks = EffectBlockPersistentState
                .get(source.getWorld())
                .getBlocksInChunkPos(Registry.BLOCK.getId(lanternType.getAssociatedBlock()), player.getChunkPos());

        if (blocks.isEmpty()) {
            player.sendMessage(Text.literal("No active ").append(lanternType.getDisplayName()).append(" lanterns found in chunk."));
            return 1;
        }

        String blocksCount = String.valueOf(blocks.size());

        MutableText text = Text.literal("Found ")
                .append(blocksCount)
                .append(" active ")
                .append(lanternType.getDisplayName())
                .append(" lanterns in chunk:\n");

        // Effectively final variable bruh moment.
        AtomicInteger idx = new AtomicInteger(0);
        do {
            BlockPos blockPos = blocks.get(idx.get());

            text.append(Text.literal("[").append(blockPos.toShortString()).append("]")
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
                    .append(idx.get() + 1 == blocks.size() ? "" : (idx.get() % 2 == 0 ? ", " : "\n"));
        } while (idx.incrementAndGet() < blocks.size());

        player.sendMessage(text);

        return 1;
    }
}
