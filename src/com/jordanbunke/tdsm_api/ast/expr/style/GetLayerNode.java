package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.CustomizationLayer;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.LayerTypeNode;
import com.jordanbunke.tdsm_api.util.UpdateChecker;

public final class GetLayerNode extends StyleExprNode {
    public static final String GET = "get_layer", HAS = "has_layer";

    private final boolean get;

    private GetLayerNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final boolean get
    ) {
        super(pos, scope, get ? LayerTypeNode.get()
                : TypeNode.getBool(), args, TypeNode.getString());

        this.get = get;
    }

    public GetLayerNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        this(pos, scope, args, true);
    }

    public static GetLayerNode has(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        return new GetLayerNode(pos, scope, args, false);
    }

    @Override
    protected String funcName() {
        return get ? GET : HAS;
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        final String id = (String) arguments.get(0).evaluate(symbolTable);
        final CustomizationLayer layer = style.layers.getLayer(id);

        if (get) {
            if (layer == null)
                ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                        arguments.get(0).getPosition(),
                        "The style '" + receiver.receiver() +
                                "' does not have a layer matching the ID '" +
                                id + "'");

            UpdateChecker.get().link(layer, style);
            return layer;
        } else
            return layer != null;
    }
}
