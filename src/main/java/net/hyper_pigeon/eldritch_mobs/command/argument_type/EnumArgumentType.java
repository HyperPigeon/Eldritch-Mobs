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
import java.util.function.Predicate;

public class EnumArgumentType <E extends Enum<E> & NamedEnum> implements ArgumentType<E> {

    public final Class<E> enumClass;
    private final List<E> blacklisted;

    public EnumArgumentType(Class<E> enumClass, List<E> blacklisted) {
        this.enumClass = enumClass;
        this.blacklisted = blacklisted;
    }

    private String wrapWithQuotesIfNecessary(String input) {
        if (input.contains(" ")) return "\"" + input + "\"";
        return input;
    }

    protected HashSet<String> getSuggestions() {
        List<E> enumConstants = List.of(enumClass.getEnumConstants());
        return new HashSet<>() {{
            enumConstants.stream()
                    .filter(Predicate.not(blacklisted::contains))
                    .forEach(e -> {
                        String enumName = e.name();
                        add(enumName);                                      // Enum name (unquoted).
                        add("\"" + enumName + "\"");                        // Enum name (quoted).

                        String displayName = e.getDisplayName();
                        if (!displayName.contains(" ")) add(displayName);   // Display name (unquoted).
                        add("\"" + displayName + "\"");                     // Display name (quoted).
                    }
            );
        }};
    }

    public <S> E get(CommandContext<S> context, String name) { return context.getArgument(name, enumClass); }
    public static <S, E extends Enum<E> & NamedEnum> E get(CommandContext<S> context, String name, Class<E> enumClass) { return context.getArgument(name, enumClass); }

    @Override public E parse(StringReader reader) throws CommandSyntaxException {

        E enumConstant;
        String readString;
        int start = reader.getCursor();

        // Prioritise reading a quoted string if possible.
        if (reader.canRead() && StringReader.isQuotedStringStart(reader.peek())) {
            readString = reader.readQuotedString();

            // Attempt to read the quoted string as a display name.
            enumConstant = NamedEnum.fromDisplayName(enumClass, readString);
            if (enumConstant != null) return enumConstant;

            // If still not found, attempt to read the quoted string as an enum name.
            try { enumConstant = Enum.valueOf(enumClass, readString); }
            catch (IllegalArgumentException ignored) {}
            if (enumConstant != null) return enumConstant;
        }

        // If we haven't yet found an enum constant, try reading an unquoted string.
        reader.setCursor(start);
        readString = reader.readUnquotedString();

        // Retry reading unquoted argument as display name.
        enumConstant = NamedEnum.fromDisplayName(enumClass, readString);
        if (enumConstant != null) return enumConstant;

        // If we still haven't found an enum constant, try reading an unquoted string as an enum name.
        try { enumConstant = Enum.valueOf(enumClass, readString); }
        catch (IllegalArgumentException ignored) {}
        if (enumConstant != null) return enumConstant;

        // If we still haven't found an enum constant, throw an exception.
        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect().createWithContext(reader, enumClass.getSimpleName());
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
