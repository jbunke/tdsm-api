package com.jordanbunke.tdsm_api.ast.expr.replacement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public abstract class ReplacementPropNode extends ReplacementExprNode {
    public ReplacementPropNode(
            final TextPosition pos, final ExpressionNode scope,
            final TypeNode returnType
    ) {
        super(pos, scope, returnType, new ExpressionNode[0]);
    }

    @Override
    public String toString() {
        return receiver + funcName();
    }
}
