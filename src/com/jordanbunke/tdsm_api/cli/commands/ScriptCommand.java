package com.jordanbunke.tdsm_api.cli.commands;

import com.jordanbunke.clink.Clink;
import com.jordanbunke.tdsm_api.cli.CLI;
import com.jordanbunke.tdsm_api.cli.CommandProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class ScriptCommand {
    public static boolean process(final String path) {
        final File file = new File(path);

        if (!file.canRead())
            return true;
        else {
            try {
                CLI.setInputStream(new FileInputStream(file),
                        CommandProcessor.SCRIPT);
                Clink.writeUpdate("Running script file " +
                        Clink.highlight(path, Clink.Mode.UPDATE));
            } catch (FileNotFoundException ignored) {
                return true;
            }

            return false;
        }
    }
}
