package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Edge;
import com.jordanbunke.tdsm.data.style.Style;

public final class GetEdgeNode extends StyleExprNode {
    public static final String NAME = "get_edge";

    public GetEdgeNode(
            TextPosition pos, ExpressionNode scope, ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.getInt(), args, TypeNode.getInt());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        final int edgeIndex = (int) arguments.evaluate(symbolTable)[0];

        if (edgeIndex < 0 || edgeIndex >= Edge.values().length)
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "The edge index (" + edgeIndex + ") is invalid");
        else
            return style.getEdgePadding(Edge.values()[edgeIndex]);

        return null;
    }
}
