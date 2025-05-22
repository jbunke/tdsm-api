package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Replacement;

public final class ReplacementTypeNode extends ExtTypeNode {
    public static final String NAME = "replacement";
    private static final ReplacementTypeNode INSTANCE;

    public ReplacementTypeNode(final TextPosition position) {
        super(position);
    }

    private ReplacementTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new ReplacementTypeNode();
    }

    public static ReplacementTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof Replacement;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
