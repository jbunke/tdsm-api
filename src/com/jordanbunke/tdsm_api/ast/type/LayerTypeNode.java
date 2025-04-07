package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;

public final class LayerTypeNode extends ExtTypeNode {
    public static final String NAME = "layer";
    private static final LayerTypeNode INSTANCE;

    public LayerTypeNode(final TextPosition position) {
        super(position);
    }

    private LayerTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new LayerTypeNode();
    }

    public static LayerTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof CustomizationLayer;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
