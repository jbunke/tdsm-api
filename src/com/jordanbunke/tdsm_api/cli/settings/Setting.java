package com.jordanbunke.tdsm_api.cli.settings;

import com.jordanbunke.clink.Clink;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.utility.math.Pair;
import com.jordanbunke.tdsm_api.cli.CLI;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Setting<T> {
    public static final String ANSI = "ansi", USER = "user";

    private static final String DEF_USER = "User";

    public static final String DEF = "default",
            FALSE = "false", F = "f",
            TRUE = "true", T = "t";

    private static final Map<String, Setting<?>> map;

    public final String code;
    public final Class<T> type;
    public final T defValue;

    private final Consumer<T> onSet;
    private final Function<String, T> parser;
    private final Predicate<String> validator;

    private T value;

    static {
        map = new HashMap<>();
        make();
    }

    private Setting(
            final String code, final Class<T> type, final T defValue,
            final Consumer<T> onSet, final Predicate<String> validator
    ) {
        this.code = code;
        this.type = type;
        this.defValue = defValue;

        this.onSet = onSet;
        parser = detParser();
        this.validator = validator;

        reset();
    }

    private static Setting<Boolean> boolSetting(
            final String code, final boolean defValue,
            final Consumer<Boolean> onSet
    ) {
        return new Setting<>(code, Boolean.class, defValue, onSet, null);
    }

    private static Setting<String> stringSetting(
            final String code, final String defValue,
            final Consumer<String> onSet, final Predicate<String> validator
    ) {
        return new Setting<>(code, String.class, defValue, onSet, validator);
    }

    public static String getUsername() {
        return map.get(USER).check();
    }

    public static String getType(final String code) {
        final Class<?> type = map.get(code).type;

        if (Integer.class == type)
            return TypeNode.getInt().toString();
        else if (Boolean.class == type)
            return TypeNode.getBool().toString();
        else
            return TypeNode.getString().toString();
    }

    private static void make() {
        map.put(ANSI, boolSetting(ANSI, Clink.supportsANSI(), b -> {
            if (b)
                Clink.enableANSI();
            else
                Clink.disableANSI();
        }));
        map.put(USER, stringSetting(USER, DEF_USER, CLI::setCaller,
                s -> !s.trim().isEmpty()));
        // extend here
    }

    public static Pair<Boolean, Boolean> processSet(
            final String code, final String value
    ) {
        if (!map.containsKey(code))
            return new Pair<>(true, false);

        final Setting<?> setting = map.get(code);
        final boolean valid = setting.set(value);

        return new Pair<>(valid, true);
    }

    public static boolean processCheck(final String code) {
        if (map.containsKey(code)) {
            final Setting<?> setting = map.get(code);
            CLI.writeGreyLine(setting.check());
            return false;
        }

        return true;
    }

    private Function<String, T> detParser() {
        if (Integer.class == type)
            return s -> type.cast(Integer.parseInt(s));
        else if (Boolean.class == type)
            return s -> type.cast(Boolean.parseBoolean(s));
        else
            return s -> type.cast(String.valueOf(s));
    }

    public String check() {
        return String.valueOf(value);
    }

    private void setValue(final T value) {
        this.value = value;
        onSet.accept(this.value);
    }

    public boolean set(final String value) {
        return switch (value) {
            case DEF -> {
                reset();
                yield false;
            }
            case TRUE, FALSE -> {
                if (Boolean.class == type) {
                    setValue(parser.apply(value));
                    yield false;
                }

                yield true;
            }
            default -> {
                if (Boolean.class == type) {
                    if (T.equals(value))
                        yield set(TRUE);
                    else if (F.equals(value))
                        yield set(FALSE);
                    else yield true;
                } else if (Integer.class == type) {
                    try {
                        Integer.parseInt(value);

                        if (validator == null) {
                            setValue(parser.apply(value));
                            yield false;
                        } else {
                            if (validator.test(value)) {
                                setValue(parser.apply(value));
                                yield false;
                            }

                            yield true;
                        }
                    } catch (NumberFormatException nfe) {
                        yield true;
                    }
                } else {
                    if (validator.test(value)) {
                        setValue(parser.apply(value));
                        yield false;
                    }

                    yield true;
                }
            }
        };
    }

    public void reset() {
        setValue(defValue);
    }
}
