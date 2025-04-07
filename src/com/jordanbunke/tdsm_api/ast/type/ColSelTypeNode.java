package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;

public final class ColSelTypeNode extends ExtTypeNode {
    public static final String NAME = "col_sel";
    private static final ColSelTypeNode INSTANCE;

    public ColSelTypeNode(final TextPosition position) {
        super(position);
    }

    private ColSelTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new ColSelTypeNode();
    }

    public static ColSelTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof ColorSelection;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
