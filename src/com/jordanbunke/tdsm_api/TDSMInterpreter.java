package com.jordanbunke.tdsm_api;

import com.jordanbunke.delta_time.scripting.Interpreter;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class TDSMInterpreter extends Interpreter {
    static {
        TDSMInterpreter.overrideVisitor(new TDSMVisitor());
    }

    public static TDSMInterpreter get() {
        return new TDSMInterpreter();
    }

    public static void failure(final String message, final TextPosition pos) {
        println("FAILURE: " + message + " [" + pos + "]");
    }

    // TODO - script validation
}
