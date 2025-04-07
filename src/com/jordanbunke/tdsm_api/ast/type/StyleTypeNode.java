package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.style.Style;

public final class StyleTypeNode extends ExtTypeNode {
    public static final String NAME = "style";
    private static final StyleTypeNode INSTANCE;

    public StyleTypeNode(final TextPosition position) {
        super(position);
    }

    private StyleTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new StyleTypeNode();
    }

    public static StyleTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof Style;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
