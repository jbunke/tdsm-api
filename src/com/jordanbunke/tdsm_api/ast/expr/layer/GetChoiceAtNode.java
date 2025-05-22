package com.jordanbunke.tdsm_api.ast.expr.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.ChoosingLayer;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;

public final class GetChoiceAtNode extends LayerExprNode {
    public static final String NAME = "get_choice_at";

    public GetChoiceAtNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.getString(), args, TypeNode.getInt());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public String evaluate(final SymbolTable symbolTable) {
        final CustomizationLayer layer = getLayer(symbolTable);

        final int index = (int) arguments.get(0).evaluate(symbolTable);

        if (layer instanceof ChoosingLayer cl) {
            final int numChoices = cl.getNumChoices();

            if (index < 0) {
                ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                        arguments.get(0).getPosition(),
                        "Index cannot be negative");
                return null;
            } else if (index >= numChoices) {
                ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                        arguments.get(0).getPosition(),
                        "Index must be less than the number of possible choices");
                return null;
            }

            return cl.getChoiceIDAt(index);
        }

        ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                getPosition(),
                "Layer is not an asset choice layer or a choice layer");
        return null;
    }
}
