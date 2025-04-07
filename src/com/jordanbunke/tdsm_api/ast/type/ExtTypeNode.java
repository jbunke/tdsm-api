package com.jordanbunke.tdsm_api.ast.type;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public abstract class ExtTypeNode extends TypeNode {
    public ExtTypeNode(
            final TextPosition position
    ) {
        super(position);
    }

    @Override
    public final boolean hasSize() {
        return false;
    }

    @Override
    public final boolean equals(final Object o) {
        return getClass().equals(o.getClass()) ||
                (o instanceof BaseTypeNode t && t.isWildcard());
    }

    @Override
    public final int hashCode() {
        return 0;
    }
}
