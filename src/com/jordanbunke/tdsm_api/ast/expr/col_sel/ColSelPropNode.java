package com.jordanbunke.tdsm_api.ast.expr.col_sel;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public abstract class ColSelPropNode extends ColSelExprNode {
    public ColSelPropNode(
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
