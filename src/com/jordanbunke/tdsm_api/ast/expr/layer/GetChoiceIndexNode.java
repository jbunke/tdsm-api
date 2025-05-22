package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.ChoosingLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;

public final class GetChoiceIndexNode extends LayerExprNode {
    public static final String NAME = "get_choice_index";

    public GetChoiceIndexNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.getInt(), args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        if (layer instanceof ChoosingLayer cl)
            return cl.getChoiceIndex();

        ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                getPosition(),
                "Layer is not an asset choice layer or a choice layer");
        return null;
    }
}
