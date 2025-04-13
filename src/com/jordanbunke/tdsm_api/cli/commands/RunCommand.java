package com.jordanbunke.tdsm_api.cli.commands;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.tdsm_api.TDSMInterpreter;

import java.io.File;
import java.nio.file.Path;

public final class RunCommand {
    public static boolean process(final String pathString) {
        final File file = new File(pathString);

        if (!file.canRead())
            return true;
        else {
            final Path path = Path.of(pathString);
            final String content = FileIO.readFile(path);

            if (content == null)
                return true;

            TDSMInterpreter.get().runScript(content, path);
            return false;
        }
    }
}
