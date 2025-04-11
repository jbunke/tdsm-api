package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class IsLockedNode extends LayerExprNode {
    public static final String NAME = "is_locked";

    public IsLockedNode(
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
        return getLayer(symbolTable).isLocked();
    }
}
