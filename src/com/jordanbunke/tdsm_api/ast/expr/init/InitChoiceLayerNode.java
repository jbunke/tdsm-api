package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.ChoiceLayer;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;

public final class InitChoiceLayerNode extends InitExprNode {
    public static final String NAME = "choice_layer";

    public InitChoiceLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                TypeNode.arrayOf(TypeNode.getString()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public ChoiceLayer evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final String[] choices = ((ScriptArray) vs[1]).stream()
                .map(String::valueOf).toArray(String[]::new);

        if (id.isEmpty()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "Layer ID must be non-empty");
            return null;
        } else if (choices.length == 0) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(1).getPosition(),
                    "Choice layer must contain at least one choice");
            return null;
        }

        return new ChoiceLayer(id, choices);
    }
}
