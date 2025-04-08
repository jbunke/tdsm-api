package com.jordanbunke.tdsm_api;

import com.jordanbunke.delta_time.io.FileIO;

import java.nio.file.Path;

public class Runner {
    public static void main(String[] args) {
        final String fp = "C:\\Users\\Jordan\\Documents\\code\\_misc\\tdsm\\scripts\\simple_export.tds";
        final Path path = Path.of(fp);
        final String content = FileIO.readFile(path);

        TDSMInterpreter.get().runScript(content, path);
    }
}
