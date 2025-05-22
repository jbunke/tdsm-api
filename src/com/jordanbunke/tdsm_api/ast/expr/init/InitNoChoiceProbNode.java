package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.NoAssetChoice;
import com.jordanbunke.tdsm_api.ast.type.NoChoiceTypeNode;

public final class InitNoChoiceProbNode extends InitExprNode {
    public static final String NAME = "no_choice_prob";

    public InitNoChoiceProbNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, NoChoiceTypeNode.get(), args, TypeNode.getFloat());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final double prob = (double) arguments.get(0).evaluate(symbolTable);

        return NoAssetChoice.prob(prob);
    }
}
