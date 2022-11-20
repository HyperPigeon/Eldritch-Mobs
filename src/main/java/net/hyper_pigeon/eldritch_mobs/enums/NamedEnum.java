package net.hyper_pigeon.eldritch_mobs.enums;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public interface NamedEnum {
    String getDisplayName();

    static <E extends Enum<E> & NamedEnum> @Nullable E fromDisplayName(Class<E> enumClass, String displayName) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.getDisplayName().equals(displayName))
                .findFirst()
                .orElse(null);
    }
}
