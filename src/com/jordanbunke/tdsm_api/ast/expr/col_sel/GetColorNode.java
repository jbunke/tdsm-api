package com.jordanbunke.tdsm_api.ast.expr.col_sel;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

import java.awt.*;

public final class GetColorNode extends ColSelExprNode {
    public static final String NAME = "get_color";

    public GetColorNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.getColor(), args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Color evaluate(final SymbolTable symbolTable) {
        return getColSel(symbolTable).getColor();
    }
}
