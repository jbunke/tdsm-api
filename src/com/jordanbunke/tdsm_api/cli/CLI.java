package com.jordanbunke.tdsm_api.cli;

import com.jordanbunke.clink.Clink;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.tdsm.ProgramInfo;
import com.jordanbunke.tdsm_api.cli.commands.HelpCommand;
import com.jordanbunke.tdsm_api.cli.settings.Setting;
import com.jordanbunke.tdsm_api.cli.util.StringProc;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.jordanbunke.tdsm_api.cli.CommandProcessor.*;

public final class CLI {
    private static final String CONT = "_";

    static SymbolTable symbolTable;

    private static String caller;

    public static void main(final String[] args) {
        setup();
        welcomeMessage();
        commandCycle();

        // TODO - closing
    }

    private static void setup() {
        Clink.setPromptEnd(" > ");

        caller = Setting.getUsername();
    }

    private static void welcomeMessage() {
        ProgramInfo.readProgramFile(false);

        final String[] lines = new String[] {
                "Command-line interface for " + ProgramInfo.PROGRAM_NAME +
                        " " + ProgramInfo.getVersion(),
                "(c) 2025 Jordan Bunke",
                StringProc.altHighlight(Clink.Mode.UPDATE,
                        "Type ", HelpCommand.HELP, " to get started")
        };

        final int max = Arrays.stream(lines)
                .peek(Clink::writeUpdate).mapToInt(String::length)
                .reduce(0, Math::max);

        Clink.writeUpdate("-".repeat(max));
    }

    private static void commandCycle() {
        while (true) {
            if (!(Clink.isStdIn() || Clink.hasNext()))
                setInputStream(System.in, Setting.getUsername());

            prompt();

            final StringBuilder commandBuilder = new StringBuilder();
            boolean cont = true;

            while (cont) {
                String line = Clink.readLine();

                cont = line.endsWith(CONT);

                if (cont)
                    line = line.substring(0, line.length() - CONT.length()) + " ";

                commandBuilder.append(line);
            }

            final String command = commandBuilder.toString().trim();

            if (!Clink.isStdIn())
                echoCommand(command);

            if (QUIT.equals(command))
                break;
            else
                process(command);
        }
    }

    private static void prompt() {
        Clink.writePrompt(caller);
    }

    private static void echoCommand(final String command) {
        writeGreyLine(command);
    }

    public static void writeGreyLine(final String line) {
        final String formatted = Clink.CLI_TEXT_GREY_BOLD +
                line + Clink.CLI_TEXT_RESET;
        Clink.write(formatted, true);
    }

    public static void setInputStream(
            final InputStream in, final String caller
    ) {
        Clink.setInputStream(in);
        CLI.caller = caller;
    }

    static void reset() {
        symbolTable = new SymbolTable(CLIScopeNode.get(),
                null, Paths.get("").toAbsolutePath());

        Clink.writeUpdate("Reset symbol table");
    }
}
