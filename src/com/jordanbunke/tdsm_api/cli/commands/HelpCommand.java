package com.jordanbunke.tdsm_api.cli.commands;

import com.jordanbunke.clink.Clink;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.utility.math.Pair;
import com.jordanbunke.tdsm.ProgramInfo;
import com.jordanbunke.tdsm_api.cli.CLI;
import com.jordanbunke.tdsm_api.cli.settings.Setting;

import java.util.Arrays;
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

        Clink.writeUpdate(lines("List of valid commands:",
                "(Keywords surrounded by " + placeholder("") + " are placeholders)") +
                commandDocumentation(new Pair<>(CHECK_W_ARGS,
                        "Retrieves the current value of " + SETTING)) +
                commandDocumentation(new Pair<>(DEF_FUNC,
                        "Defines a DeltaScript function " + FUNC)) +
                commandDocumentation(new Pair<>(EVAL_EXPR,
                        "Evaluates a DeltaScript expression " + EXPR)) +
                commandDocumentation(new Pair<>(HELP,
                                "Lists valid commands and their use"),
                        new Pair<>(HELP_CODE,
                                "Detailed explanation of commands and concepts related to the API"),
                        new Pair<>(HELP_SETTINGS,
                                "Detailed explanation of commands related to CLI settings")) +
                commandDocumentation(new Pair<>(QUIT,
                        "Quits the command-line interface")) +
                commandDocumentation(new Pair<>(RESET,
                        "Resets the DeltaScript symbol table, deleting all functions and variables (irreversible operation)")) +
                commandDocumentation(new Pair<>(RUN_PATH,
                        "Attempts to interpret and run the file at " +
                                PATH + " as a DeltaScript script")) +
                commandDocumentation(new Pair<>(SCRIPT_PATH,
                        "Executes the commands in the text file at " + PATH)) +
                commandDocumentation(new Pair<>(SET_W_ARGS,
                        "Sets " + SETTING + " to " + VALUE)) +
                commandDocumentation(new Pair<>(STATUS,
                        "Shows the variables and functions currently defined in the symbol table")) +
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
        final String API_SPEC_URL = "https://github.com/jbunke/tdsm-api",
                DELTASCRIPT_URL = "https://github.com/jbunke/deltascript",
                FUNC_GRAMMAR_SUB_PATH = "/blob/master/docs/ls-6-func.md#632--helper-functions",
                EXPR_SUB_PATH = "/blob/master/docs/ls-4-expr.md",
                STAT_SUB_PATH = "/blob/master/docs/ls-5-stat.md",
                PATH = placeholder("path"),
                RUN_PATH = assembleCommand(RUN, PATH),
                EXPR = placeholder("expr"),
                EVAL_EXPR = assembleCommand(EVAL, EXPR),
                FUNC = placeholder("func"),
                DEF_FUNC = assembleCommand(DEF, FUNC),
                STAT = placeholder("stat"),
                prg = ProgramInfo.PROGRAM_NAME;

        Clink.writeUpdate(
                lines("The following commands are used to interact with the " +
                                prg + " scripting API.", "",
                        prg + " scripts are written in an extension dialect of DeltaScript.",
                        "Read the API specification: " + API_SPEC_URL,
                        "Read about DeltaScript: " + DELTASCRIPT_URL, "",
                        altHighlight(Clink.Mode.UPDATE,
                                "Commands can be extended to the following line by terminating a line with ",
                                CLI.CONT, ".")) +
                        commandDetail(DEF_FUNC,
                                altHighlight(Clink.Mode.UPDATE,
                                        "Defines a function ", FUNC,
                                        " and adds it to the current symbol table."),
                                altHighlight(Clink.Mode.UPDATE,
                                        "The function can then be invoked by the ",
                                        EVAL_EXPR, " and ", STAT, " commands, as well as within future ",
                                        DEF_FUNC, " commands."),
                                altHighlight(Clink.Mode.UPDATE,
                                        "Note that resetting the symbol table (",
                                        RESET, "), will irreversibly delete the function."),
                                "", "A syntactically correct DeltaScript function is defined by the grammar rule 'helper':",
                                DELTASCRIPT_URL + FUNC_GRAMMAR_SUB_PATH) +
                        commandDetail(EVAL_EXPR,
                                altHighlight(Clink.Mode.UPDATE,
                                        "Evaluates an expression ", EXPR, "."),
                                "", "A syntactically correct DeltaScript expression is defined by the grammar rule 'expr':",
                                DELTASCRIPT_URL + EXPR_SUB_PATH) +
                        commandDetail(RUN_PATH,
                                altHighlight(Clink.Mode.UPDATE,
                                        "Runs the file at ", PATH, " as a " + prg + " script."),
                                "Script header functions must be void return and accept no parameters.",
                                "Note that a script execution runs with its own symbol table; it does not use the CLI symbol table.",
                                prg + " scripts conventionally use the extension '.tds'."
                                /* TODO - use public constant for extension in
                                    tdsm or tdsm-api sources somewhere */) +
                        commandDetail(STATUS,
                                "Prints the current status of the CLI's symbol table.",
                                "", "Syntax",
                                "Functions: ::" + placeholder("name") +
                                        " -> final " +
                                        placeholder("return_type") + ":" +
                                        placeholder("signature"),
                                "Variables: " + placeholder("name") + " -> " +
                                        placeholder("final") + "? " +
                                        placeholder("type") + ":" +
                                        placeholder("value")) +
                        commandDetail(STAT, altHighlight(Clink.Mode.UPDATE,
                                        "Executes a statement ", STAT, "."),
                                "", "A syntactically correct DeltaScript statement is defined by the grammar rule 'stat':",
                                DELTASCRIPT_URL + STAT_SUB_PATH));
    }

    private static void printSettings() {
        final String prg = ProgramInfo.PROGRAM_NAME,
                SETTING = placeholder("setting"),
                VALUE = placeholder("value"),
                CHECK_W_ARGS = assembleCommand(CHECK, SETTING),
                SET_W_ARGS = assembleCommand(SET, SETTING, VALUE);

        Clink.writeUpdate(
                lines("The " + prg + " command-line interface has the following settings.",
                        "", "Each setting has a type:",
                        " - '" + TypeNode.getBool().toString() +
                                "' for settings that can be either 'true' or 'false' (i.e. enabled or disabled),",
                        " - '" + TypeNode.getInt().toString() +
                                "' for numeric settings",
                        " - '" + TypeNode.getString().toString() +
                                "' for textual settings", "",
                        altHighlight(Clink.Mode.UPDATE,
                                "Values of settings can be checked with ",
                                CHECK_W_ARGS, " and overwritten with ", SET_W_ARGS, "."),
                        altHighlight(Clink.Mode.UPDATE,
                                "All settings can be reset with ",
                                assembleCommand(SET, SETTING, Setting.DEF), ".")) +
                settingDetail(Setting.ANSI, "ANSI codes" +
                        " (https://en.wikipedia.org/wiki/ANSI_escape_code)" +
                        " are used to color text.", "[Recommended]") +
                settingDetail(Setting.USER, "The username")
        );
    }

    private static String commandDetail(
            final String command, final String... description
    ) {
        return altHighlight(Clink.Mode.UPDATE,
                lines("", "", ""), command + Clink.NEW_LINE,
                "-".repeat(command.length()) +
                        Clink.NEW_LINE + lines(description));
    }

    private static String settingDetail(
            final String setting, final String... description
    ) {
        final List<String> lines = new LinkedList<>();

        lines.add("Type: " + Setting.getType(setting));
        lines.add("");
        lines.addAll(Arrays.asList(description));

        return commandDetail(setting, lines.toArray(String[]::new));
    }
}
