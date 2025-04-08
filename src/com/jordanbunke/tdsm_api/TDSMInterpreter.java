package com.jordanbunke.tdsm_api;

import com.jordanbunke.delta_time.scripting.Interpreter;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

import java.nio.file.Path;

public final class TDSMInterpreter extends Interpreter {
    static {
        TDSMInterpreter.overrideVisitor(new TDSMVisitor());
    }

    public static TDSMInterpreter get() {
        return new TDSMInterpreter();
    }

    public static void failure(
            final String attempt, final String reason, final TextPosition pos
    ) {
        failure(attempt + " because " + reason, pos);
    }

    public static void failure(
            final String message, final TextPosition pos
    ) {
        // TODO: move to CLI
        println("FAILURE: " + message + " [at " + pos + "]");
    }

    public void runScript(final String content, final Path filepath) {
        final HeadFuncNode script = build(content);

        if (validateScript(script))
            run(script, filepath);
        else if (script != null)
            println("Invalid script"); // TODO: move to CLI
        else
            println("Failed to compile script at \"" + filepath + "\""); // TODO: move to CLI
    }

    private boolean validateScript(final HeadFuncNode script) {
        if (script == null) return false;

        return script.paramsMatch() && script.getReturnType() == null;
    }
}
