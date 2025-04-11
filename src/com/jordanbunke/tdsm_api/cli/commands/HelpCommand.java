package com.jordanbunke.tdsm_api.cli.commands;

public final class HelpCommand {
    private static final String PREFIX = "--",
            CODE = PREFIX + "code",
            SETTINGS = PREFIX + "settings";

    public static void printHelp() {
        // TODO
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

    }
}
