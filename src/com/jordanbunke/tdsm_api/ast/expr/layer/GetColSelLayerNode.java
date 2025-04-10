package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.ColorSelectionLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;
import com.jordanbunke.tdsm_api.util.UpdateChecker;

import java.util.Arrays;

public final class GetColSelLayerNode extends LayerExprNode {
    public static final String NAME = "get_col_sels";

    public GetColSelLayerNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.arrayOf(ColSelTypeNode.get()), args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public ScriptArray evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        if (layer instanceof ColorSelectionLayer csl) {
            final ColorSelection[] selections = csl.getSelections();

            return new ScriptArray(Arrays.stream(selections)
                    .peek(cs -> UpdateChecker.get().link(cs, layer))
                    .map(cs -> cs));
        } else {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT, getPosition(),
                    "The layer '" + receiver.receiver() +
                            "' is not a color selection layer");
        }

        return null;
    }
}
