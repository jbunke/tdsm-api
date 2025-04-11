package com.jordanbunke.tdsm_api.cli.commands;

import com.jordanbunke.clink.Clink;

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
                Clink.setInputStream(new FileInputStream(file));
                Clink.writeUpdate("Running script file " +
                        Clink.highlight(path, Clink.Mode.UPDATE));
            } catch (FileNotFoundException ignored) {
                return true;
            }

            return false;
        }
    }
}
