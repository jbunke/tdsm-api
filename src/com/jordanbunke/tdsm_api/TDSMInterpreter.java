package com.jordanbunke.tdsm_api;

import com.jordanbunke.delta_time.scripting.Interpreter;

public final class TDSMInterpreter extends Interpreter {
    static {
        TDSMInterpreter.overrideVisitor(new TDSMVisitor());
    }

    public static TDSMInterpreter get() {
        return new TDSMInterpreter();
    }

    // TODO - script validation
}
