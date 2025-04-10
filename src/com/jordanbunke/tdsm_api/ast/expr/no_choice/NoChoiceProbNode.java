package com.jordanbunke.tdsm_api.ast.expr.no_choice;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class NoChoiceProbNode extends NoChoiceExprNode {
    public static final String NAME = "prob";

    public NoChoiceProbNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.getFloat(), args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Double evaluate(final SymbolTable symbolTable) {
        return getNoChoice(symbolTable).randomProb;
    }
}
