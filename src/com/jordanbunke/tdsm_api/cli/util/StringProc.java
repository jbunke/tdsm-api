package com.jordanbunke.tdsm_api.cli.util;

import com.jordanbunke.clink.Clink;

import java.util.Arrays;

public final class StringProc {
    public static String altHighlight(
            final Clink.Mode resumeMode, final String... message
    ) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length; i++) {
            final String comp = message[i];

            sb.append(i % 2 == 0 ? comp : Clink.highlight(comp, resumeMode));
        }

        return sb.toString();
    }

    public static String lines(final String... lines) {
        return Arrays.stream(lines)
                .reduce((a, b) -> a + Clink.NEW_LINE + b).orElse("");
    }

    public static String assembleCommand(final String... comps) {
        return Arrays.stream(comps).reduce((a, b) -> a + " " + b).orElse("");
    }

    public static String placeholder(final String keyword) {
        final String OPEN = "<", CLOSE = ">";
        return OPEN + keyword + CLOSE;
    }
}
