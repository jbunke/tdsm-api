package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.Edge;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.TDSMInterpreter;

public final class SetEdgeNode extends StyleStatNode {
    public static final String NAME = "set_edge";

    public SetEdgeNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.expectExact(
                TypeNode.getInt(), TypeNode.getInt()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        final Object[] vs = arguments.evaluate(symbolTable);

        final int edgeIndex = (int) vs[0], px = (int) vs[1];

        if (edgeIndex < 0 || edgeIndex >= Edge.values().length)
            TDSMInterpreter.failure("Couldn't set padded/cropped edge",
                    "the edge index (" + edgeIndex + ") is invalid",
                    arguments.get(0).getPosition());
        else {
            final Edge edge = Edge.values()[edgeIndex];

            if (!style.validateEdgePadding(edge, px))
                TDSMInterpreter.failure("Couldn't set padded/cropped edge",
                        "augmented sprite size would be out of bounds",
                        getPosition());
            else
                style.setEdgePadding(edge, px);
        }

        return FuncControlFlow.cont();
    }
}
