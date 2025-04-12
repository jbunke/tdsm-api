package com.jordanbunke.tdsm_api.cli;

import com.jordanbunke.clink.Clink;
import com.jordanbunke.delta_time.scripting.util.PathHelper;
import com.jordanbunke.delta_time.utility.math.Pair;
import com.jordanbunke.tdsm_api.cli.commands.*;
import com.jordanbunke.tdsm_api.cli.settings.Setting;
import com.jordanbunke.tdsm_api.cli.util.StringProc;

import java.util.Arrays;
import java.util.stream.Stream;

public final class CommandProcessor {
    public static final String QUIT = "quit",
            RESET = "reset",
            SCRIPT = "script",
            CHECK = "check", SET = "set",
            DEF = "def", EVAL = "eval", RUN = "run";

    static void process(final String command) {
        if (command.isEmpty())
            return;

        switch (command) {
            // commands without args
            case HelpCommand.HELP -> HelpCommand.printHelp();
            case RESET -> CLI.reset();
            default -> processComplex(command);
        }
    }

    private static void processComplex(final String command) {
        final String[] comps = splitCommand(command);

        switch (comps[0]) {
            case HelpCommand.HELP -> {
                if (comps.length != 2)
                    syntaxError("", HelpCommand.HELP, " command must have ",
                            String.valueOf(0), " or ",
                            String.valueOf(1), " argument(s)");
                else if (HelpCommand.process(comps[1]))
                    syntaxError("The argument ", comps[1],
                            " is invalid for the ", HelpCommand.HELP,
                            " command");
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
            case CHECK -> {
                if (comps.length != 2)
                    syntaxError("", CHECK, " command must have ",
                            String.valueOf(1), " argument");
                else if (Setting.processCheck(comps[1]))
                    syntaxError("The code ", comps[1],
                            " does not correspond to a setting");
            }
            case SET -> {
                if (comps.length != 3)
                    syntaxError("", SET, " command must have ",
                            String.valueOf(2), " arguments");
                else {
                    final String code = comps[1], value = comps[2];
                    final Pair<Boolean, Boolean> ret =
                            Setting.processSet(code, value);

                    if (ret.a())
                        syntaxError(ret.b() ? new String[] {
                                "The value ", value,
                                " is invalid for the setting ", code
                        } : new String[] {
                                "The code ", code,
                                " does not correspond to a setting"
                        });
                }
            }
            // TODO - extend here
            default -> {
                // TODO - attempt to process as DeltaScript statement
            }
        }

        // TODO - no first comp match
    }

    private static String[] splitCommand(final String command) {
        return Arrays.stream(command.split(" "))
                .filter(s -> !s.trim().isEmpty()).toArray(String[]::new);
    }

    private static String path(final Stream<String> components) {
        return PathHelper.formatPathString(
                components.reduce("", String::concat));
    }

    private static void syntaxError(final String... message) {
        Clink.writeError(StringProc.altHighlight(Clink.Mode.ERROR, message));
    }
}
