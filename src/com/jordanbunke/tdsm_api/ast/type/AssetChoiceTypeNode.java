package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.AssetChoiceTemplate;

public final class AssetChoiceTypeNode extends ExtTypeNode {
    public static final String NAME = "asset_choice";
    private static final AssetChoiceTypeNode INSTANCE;

    public AssetChoiceTypeNode(final TextPosition position) {
        super(position);
    }

    private AssetChoiceTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new AssetChoiceTypeNode();
    }

    public static AssetChoiceTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof AssetChoiceTemplate;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
