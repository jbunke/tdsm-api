package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.ColorSelectionLayer;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;

public final class InitColSelLayerNode extends InitExprNode {
    public static final String NAME = "col_sel_layer";

    public InitColSelLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                TypeNode.arrayOf(ColSelTypeNode.get()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public ColorSelectionLayer evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final ColorSelection[] selections = ((ScriptArray) vs[1]).stream()
                .map(o -> (ColorSelection) o)
                .toArray(ColorSelection[]::new);

        if (id.isEmpty()) {
            ScriptErrorLog.runtimeError(arguments.get(0).getPosition(),
                    "Layer ID must be non-empty");
            return null;
        } else if (selections.length == 0) {
            ScriptErrorLog.runtimeError(arguments.get(1).getPosition(),
                    "Color selection layer must consist of at least one color selection");
            return null;
        }

        return new ColorSelectionLayer(id, selections);
    }
}
