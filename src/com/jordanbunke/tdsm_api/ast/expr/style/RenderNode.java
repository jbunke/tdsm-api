package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class RenderNode extends StyleExprNode {
    public static final String NAME = "render";

    public RenderNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.getImage(), args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        return getStyle(symbolTable).renderSpriteSheet();
    }
}
