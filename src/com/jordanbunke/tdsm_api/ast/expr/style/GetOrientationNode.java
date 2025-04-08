package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Orientation;

public final class GetOrientationNode extends StyleExprNode {
    public static final String NAME = "get_orientation";

    public GetOrientationNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.getBool(), args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        return Orientation.VERTICAL == getStyle(symbolTable)
                .getAnimationOrientation();
    }
}
