package net.hyper_pigeon.eldritch_mobs.command.argument_type;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.hyper_pigeon.eldritch_mobs.EldritchMobsMod;
import net.hyper_pigeon.eldritch_mobs.enums.LanternType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LanternArgumentType implements ArgumentType<LanternType> {

    private static final Set<String> EXAMPLES = new HashSet<>() {{
        addAll(LanternType.LANTERN_TYPES.stream().map(LanternType::name).toList());
        addAll(LanternType.LANTERN_TYPES.stream().map(LanternType::getDisplayName).toList());
    }};

    public static LanternArgumentType lantern() {
        return new LanternArgumentType();
    }

    public static <S> LanternType getLanternType(CommandContext<S> context, String name) {
        return context.getArgument(name, LanternType.class);
    }

    @Override public LanternType parse(StringReader reader) throws CommandSyntaxException {

        String string = reader.getRemaining();
        reader.setCursor(reader.getTotalLength());

        LanternType lanternType;
        try {
            lanternType = LanternType.fromDisplayName(string);
            if (lanternType == null) lanternType = LanternType.valueOf(string);
        } catch (IllegalArgumentException e) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect().createWithContext(reader, string);
        }

        return lanternType;
    }

    @Override public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader reader = new StringReader(builder.getInput());

        reader.setCursor(builder.getStart());
        builder = builder.createOffset(reader.getCursor());

        CommandSource.suggestMatching(LanternType.LANTERN_TYPES.stream().map(LanternType::toString), builder);
        CommandSource.suggestMatching(LanternType.LANTERN_TYPES.stream().map(LanternType::getDisplayName), builder);

        return builder.buildFuture();
    }

    @Override public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
