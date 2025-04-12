package com.jordanbunke.tdsm_api.cli.commands;

import com.jordanbunke.clink.Clink;
import com.jordanbunke.delta_time.utility.math.Pair;

import java.util.LinkedList;
import java.util.List;

import static com.jordanbunke.tdsm_api.cli.CommandProcessor.*;
import static com.jordanbunke.tdsm_api.cli.util.StringProc.*;

public final class HelpCommand {
    public static final String HELP = "help";
    private static final String PREFIX = "--",
            CODE = PREFIX + "code",
            SETTINGS = PREFIX + "settings";

    private static final int COLUMNS = 24;

    public static void printHelp() {
        final String HELP_CODE = assembleCommand(HELP, CODE),
                HELP_SETTINGS = assembleCommand(HELP, SETTINGS),
                PATH = placeholder("path"),
                RUN_PATH = assembleCommand(RUN, PATH),
                SCRIPT_PATH = assembleCommand(SCRIPT, PATH),
                EXPR = placeholder("expr"),
                EVAL_EXPR = assembleCommand(EVAL, EXPR),
                FUNC = placeholder("func"),
                DEF_FUNC = assembleCommand(DEF, FUNC),
                SETTING = placeholder("setting"),
                VALUE = placeholder("value"),
                CHECK_W_ARGS = assembleCommand(CHECK, SETTING),
                SET_W_ARGS = assembleCommand(SET, SETTING, VALUE),
                STAT = placeholder("stat");

        Clink.writeUpdate("Commands:" +
                commandDocumentation(
                        new Pair<>(HELP, "Lists valid commands and their use"),
                        new Pair<>(HELP_CODE, "Explains commands and concepts related to the API"),
                        new Pair<>(HELP_SETTINGS, "Explains commands related to CLI settings")) +
                commandDocumentation(new Pair<>(CHECK_W_ARGS, "")) +
                commandDocumentation(new Pair<>(DEF_FUNC, "")) +
                commandDocumentation(new Pair<>(EVAL_EXPR, "")) +
                commandDocumentation(new Pair<>(QUIT,
                        "Quits the command-line interface")) +
                commandDocumentation(new Pair<>(RESET,
                        "Resets the DeltaScript symbol table, deleting all functions and variables.")) +
                commandDocumentation(new Pair<>(RUN_PATH,
                        "Attempts to interpret and run the file at " +
                                PATH + " as a DeltaScript script")) +
                commandDocumentation(new Pair<>(SCRIPT_PATH,
                        "Executes the commands in the text file at " + PATH)) +
                commandDocumentation(new Pair<>(SET_W_ARGS, "")) +
                commandDocumentation(new Pair<>(STAT,
                        "Executes a DeltaScript statement " + STAT)));
    }

    @SafeVarargs
    private static String commandDocumentation(
            final Pair<String, String>... syntaxDesc
    ) {
        final List<String> alts = new LinkedList<>();

        alts.add(Clink.NEW_LINE);

        for (Pair<String, String> p : syntaxDesc) {
            alts.add(Clink.NEW_LINE + p.a());
            alts.add(description(p.a(), p.b()));
        }

        return altHighlight(Clink.Mode.UPDATE, alts.toArray(String[]::new));
    }

    private static String description(
            final String syntax, final String description
    ) {
        return " ".repeat(COLUMNS - syntax.length()) +
                "- " + description;
    }

    public static boolean process(final String arg) {
        switch (arg) {
            case CODE -> printCode();
            case SETTINGS -> printSettings();
            default -> {
                return true;
            }
        }

        return false;
    }

    private static void printCode() {
        // TODO
    }

    private static void printSettings() {
        // TODO
    }
}
