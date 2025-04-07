package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm_api.util.Tokens;

public abstract class ConstNode extends GlobalExprNode {
    public ConstNode(final TextPosition pos, final TypeNode returnType) {
        super(pos, returnType, Arguments.none());
    }

    @Override
    public String toString() {
        return "$" + Tokens.GLOBAL_NAMESPACE + "." + funcName();
    }
}
