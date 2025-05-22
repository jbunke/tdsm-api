package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.layer.GroupLayer;
import com.jordanbunke.tdsm.util.StringUtils;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;

public final class InitGroupLayerNode extends InitExprNode {
    public static final String NAME = "group_layer";

    public InitGroupLayerNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, LayerTypeNode.get(), args, TypeNode.getString(),
                TypeNode.arrayOf(LayerTypeNode.get()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public CustomizationLayer evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String id = (String) vs[0];
        final CustomizationLayer[] members = ((ScriptArray) vs[1]).stream()
                .map(o -> (CustomizationLayer) o)
                .toArray(CustomizationLayer[]::new);

        if (id.isEmpty()) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "Layer ID must be non-empty");
            return null;
        }

        return new GroupLayer(id, StringUtils.nameFromID(id), members);
    }
}
