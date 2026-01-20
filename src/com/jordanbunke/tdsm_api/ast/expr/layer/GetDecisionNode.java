package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.DecisionLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;

public final class GetDecisionNode extends LayerExprNode {
    public static final String NAME = "get_decision";

    public GetDecisionNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, LayerTypeNode.get(), args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public CustomizationLayer evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        if (layer instanceof DecisionLayer dl)
            return dl.getDecision();
        else
            ScriptErrorLog.runtimeError(getPosition(),
                    "The layer '" + receiver.receiver() + "' is not a decision layer");

        return null;
    }
}
