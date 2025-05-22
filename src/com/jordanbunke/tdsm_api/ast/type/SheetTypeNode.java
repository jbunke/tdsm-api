package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.sprite.SpriteSheet;

public final class SheetTypeNode extends ExtTypeNode {
    public static final String NAME = "sheet";
    private static final SheetTypeNode INSTANCE;

    public SheetTypeNode(final TextPosition position) {
        super(position);
    }

    private SheetTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new SheetTypeNode();
    }

    public static SheetTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof SpriteSheet;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
