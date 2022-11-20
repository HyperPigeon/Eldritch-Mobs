package net.hyper_pigeon.eldritch_mobs.command.argument_type;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.hyper_pigeon.eldritch_mobs.enums.NamedEnum;
import net.minecraft.command.CommandSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnumArgumentType <E extends Enum<E> & NamedEnum> implements ArgumentType<E> {

    public final Class<E> enumClass;

    public EnumArgumentType(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    protected HashSet<String> getSuggestions() {
        List<E> enumConstants = List.of(enumClass.getEnumConstants());
        return new HashSet<>() {{
            enumConstants.forEach(e -> {
                add(e.name());
                add(e.getDisplayName());
            });
        }};
    }

    public <S> E get(CommandContext<S> context, String name) { return context.getArgument(name, enumClass); }
    public static <S, E extends Enum<E> & NamedEnum> E get(CommandContext<S> context, String name, Class<E> enumClass) { return context.getArgument(name, enumClass); }

    @Override public E parse(StringReader reader) throws CommandSyntaxException {
        String string = reader.getRemaining();
        reader.setCursor(reader.getTotalLength());

        E enumConstant;
        try {
            enumConstant = NamedEnum.fromDisplayName(enumClass, string);
            if (enumConstant == null) enumConstant = Enum.valueOf(enumClass, string);
        } catch (IllegalArgumentException | NullPointerException ignored) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect().createWithContext(reader, string);
        }

        return enumConstant;
    }

    @Override public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader reader = new StringReader(builder.getInput());

        reader.setCursor(builder.getStart());
        builder = builder.createOffset(reader.getCursor());

        CommandSource.suggestMatching(getSuggestions(), builder);

        return builder.buildFuture();
    }

    @Override public Collection<String> getExamples() {
        return getSuggestions();
    }
}
