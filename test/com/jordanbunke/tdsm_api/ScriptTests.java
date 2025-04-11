package com.jordanbunke.tdsm_api;

import com.jordanbunke.clink.Clink;
import com.jordanbunke.tdsm.util.ParserUtils;
import org.junit.Test;

import java.nio.file.Path;

public class ScriptTests {
    private static final Path FOLDER = Path.of("scripts");
    private static final String EXT = ".tds", QUIT = "!q";

    @Test
    public void all() {
        final String[] scriptCodes = readScriptsCSV();

        for (String scriptCode : scriptCodes)
            runScript(scriptCode);
    }


    public static void main(final String[] args) {
        try {
            String code = "";

            while (!QUIT.equals(code)) {
                if (!code.isEmpty()) runScript(code);
                code = Clink.promptForString("Script code or " +
                        Clink.highlight(QUIT, Clink.Mode.PROMPT) + " to quit");
            }
        } catch (NullPointerException npe) {
            Clink.writeError("No such script code");
            main(args);
        }
    }

    private static void runScript(final String scriptCode) {
        final Path path = FOLDER.resolve(scriptCode + EXT);
        final String script = ParserUtils.read(path);
        Clink.writeUpdate("Running " +
                Clink.highlight(scriptCode, Clink.Mode.UPDATE));
        TDSMInterpreter.get().runScript(script, path);
    }

    private static String[] readScriptsCSV() {
        final Path path = Path.of("scripts.csv");
        return ParserUtils.read(path).split(",");
    }
}
