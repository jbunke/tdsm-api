package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.TDSMInterpreter;

import java.util.Arrays;

public final class SetPaddingNode extends StyleStatNode {
    public static final String NAME = "set_padding";

    public SetPaddingNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.expectExact(
                TypeNode.getInt(), TypeNode.getInt(),
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
        final int[] edges = Arrays.stream(vs).mapToInt(e -> (int) e).toArray();

        final int l = edges[0], r = edges[1], t = edges[2], b = edges[3];

        if (!style.validateEdgePadding(l, r, t, b))
            TDSMInterpreter.failure("Couldn't set padding",
                    "augmented sprite size would be out of bounds",
                    getPosition());
        else
            style.setEdgePadding(l, r, t, b);

        return FuncControlFlow.cont();
    }
}
