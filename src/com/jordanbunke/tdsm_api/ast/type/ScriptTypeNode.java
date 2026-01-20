package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm_api.util.TDSMScript;

public final class ScriptTypeNode extends ExtTypeNode {
    public static final String NAME = "script";
    private static final ScriptTypeNode INSTANCE;

    public ScriptTypeNode(final TextPosition position) {
        super(position);
    }

    private ScriptTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new ScriptTypeNode();
    }

    public static ScriptTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof TDSMScript;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
