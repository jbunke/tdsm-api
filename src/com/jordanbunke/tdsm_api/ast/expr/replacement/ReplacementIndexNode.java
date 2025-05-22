package com.jordanbunke.tdsm_api.ast.expr.replacement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class ReplacementIndexNode extends ReplacementPropNode {
    public static final String NAME = "index";

    public ReplacementIndexNode(
            final TextPosition pos, final ExpressionNode scope
    ) {
        super(pos, scope, TypeNode.getInt());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return getReplacement(symbolTable).index();
    }
}
