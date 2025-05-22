package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm_api.util.Tokens;

public abstract class InitConstNode extends InitExprNode {
    public InitConstNode(final TextPosition pos, final TypeNode returnType) {
        super(pos, returnType, Arguments.none());
    }

    @Override
    public String toString() {
        return "$" + Tokens.INIT_NAMESPACE + "." + funcName();
    }
}
