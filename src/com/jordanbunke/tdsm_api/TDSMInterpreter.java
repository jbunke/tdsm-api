package com.jordanbunke.tdsm_api;

import com.jordanbunke.clink.Clink;
import com.jordanbunke.delta_time.scripting.Interpreter;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.Error;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

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
        runScript(build(content), filepath, null);
    }

    public Object runScript(
            final HeadFuncNode script, final Path filepath,
            final TypeNode returnType, final TypeNode... paramSpec
    ) {
        if (MetaFuncHelper.validate(script, returnType, paramSpec))
            return run(script, filepath);
        else if (script != null)
            println("Invalid script"); // TODO: move to CLI
        else
            println("Failed to compile script at \"" + filepath + "\""); // TODO: move to CLI

        return null;
    }

    @Override
    public void displayErrors() {
        final Error[] errors = ScriptErrorLog.getErrors();

        for (Error error : errors)
            Clink.writeError(error.toString());
    }
}
