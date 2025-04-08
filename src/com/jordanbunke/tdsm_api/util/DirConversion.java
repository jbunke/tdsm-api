package com.jordanbunke.tdsm_api.util;

import com.jordanbunke.tdsm.data.Directions.Dir;

import static com.jordanbunke.tdsm.data.Directions.Dir.*;

public final class DirConversion {
    private static final String N = "N", W = "W", E = "E", S = "S";

    public static String from(final Dir dir) {
        return switch (dir) {
            case UP -> N;
            case LEFT -> W;
            case RIGHT -> E;
            case DOWN -> S;
            default -> dir.name();
        };
    }

    public static Dir to(final String code) {
        return switch (code) {
            case N -> UP;
            case W -> LEFT;
            case E -> RIGHT;
            case S -> DOWN;
            default -> valueOf(code);
        };
    }
}
