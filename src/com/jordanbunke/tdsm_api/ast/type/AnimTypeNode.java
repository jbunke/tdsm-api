package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.anim.data.Animation;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class AnimTypeNode extends ExtTypeNode {
    public static final String NAME = "anim";
    private static final AnimTypeNode INSTANCE;

    public AnimTypeNode(final TextPosition position) {
        super(position);
    }

    private AnimTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new AnimTypeNode();
    }

    public static AnimTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof Animation;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
