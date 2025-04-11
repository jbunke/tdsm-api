package com.jordanbunke.tdsm_api;

import com.jordanbunke.tdsm.util.ParserUtils;

import java.nio.file.Path;

public class Runner {
    private static final Path FOLDER = Path.of("scripts");
    private static final String EXT = ".tds";

    public static void main(String[] args) {
        final String[] scriptCodes = readScriptsCSV();

        for (String scriptCode : scriptCodes) {
            final Path path = FOLDER.resolve(scriptCode + EXT);
            final String script = ParserUtils.read(path);
            System.out.println("Running \"" + scriptCode + "\"");
            TDSMInterpreter.get().runScript(script, path);
        }
    }

    private static String[] readScriptsCSV() {
        final Path path = Path.of("scripts.csv");
        return ParserUtils.read(path).split(",");
    }
}
