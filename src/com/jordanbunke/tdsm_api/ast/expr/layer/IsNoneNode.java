package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.AssetChoiceLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;

public final class IsNoneNode extends LayerExprNode {
    public static final String NAME = "is_none";

    public IsNoneNode(
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
        final CustomizationLayer layer = getLayer(symbolTable);

        if (layer instanceof AssetChoiceLayer acl)
            return !acl.hasChoice();
        else
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT, getPosition(),
                    "The layer '" + receiver.receiver() +
                            "' is not an asset choice layer");

        return false;
    }
}
