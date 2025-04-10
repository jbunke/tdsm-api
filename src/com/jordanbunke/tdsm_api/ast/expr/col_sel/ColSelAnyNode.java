package com.jordanbunke.tdsm_api.ast.expr.col_sel;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class ColSelAnyNode extends ColSelPropNode {
    public static final String NAME = "any";

    public ColSelAnyNode(
            final TextPosition pos, final ExpressionNode scope
    ) {
        super(pos, scope, TypeNode.getBool());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        return getColSel(symbolTable).isAnyColor();
    }
}
