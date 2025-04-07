package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.NoAssetChoice;

public final class NoChoiceTypeNode extends ExtTypeNode {
    public static final String NAME = "no_choice";
    private static final NoChoiceTypeNode INSTANCE;

    public NoChoiceTypeNode(final TextPosition position) {
        super(position);
    }

    private NoChoiceTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new NoChoiceTypeNode();
    }

    public static NoChoiceTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof NoAssetChoice;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
