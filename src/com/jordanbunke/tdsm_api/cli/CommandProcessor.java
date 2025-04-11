package com.jordanbunke.tdsm_api.cli;

import com.jordanbunke.clink.Clink;
import com.jordanbunke.delta_time.scripting.util.PathHelper;
import com.jordanbunke.tdsm_api.cli.commands.*;

import java.util.Arrays;
import java.util.stream.Stream;

public final class CommandProcessor {
    static final String QUIT = "quit",
            HELP = "help",
            RESET = "reset",
            SCRIPT = "script";

    static void process(final String command) {
        if (command.isEmpty())
            return;

        switch (command) {
            // commands without args
            case HELP -> HelpCommand.printHelp();
            case RESET -> CLI.reset();
            default -> processComplex(command);
        }
    }

    private static void processComplex(final String command) {
        final String[] comps = command.split(" ");

        switch (comps[0]) {
            case HELP -> {
                if (comps.length != 2)
                    syntaxError("", HELP, " command must have ",
                            String.valueOf(0), " or ",
                            String.valueOf(1), " argument(s)");
                else if (HelpCommand.process(comps[1]))
                    syntaxError("The argument ", comps[1],
                            " is invalid for the ", HELP, " command");
            }
            case SCRIPT -> {
                if (comps.length < 2)
                    syntaxError("", SCRIPT, " command must have ",
                            String.valueOf(1), " argument");
                else {
                    final String path = path(Arrays.stream(
                            comps, 1, comps.length));

                    if (ScriptCommand.process(path))
                        syntaxError("The path ", comps[1],
                                " did not contain a readable file");
                }
            }
            // TODO - extend here
            default -> {
                // TODO - attempt to process as DeltaScript statement
            }
        }

        // TODO - no first comp match
    }

    private static String path(final Stream<String> components) {
        return PathHelper.formatPathString(
                components.reduce("", String::concat));
    }

    private static void syntaxError(final String... message) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length; i++) {
            final String comp = message[i];

            sb.append(i % 2 == 0 ? comp
                    : Clink.highlight(comp, Clink.Mode.ERROR));
        }

        Clink.writeError(sb.toString());
    }
}
