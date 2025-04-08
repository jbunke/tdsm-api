package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.Orientation;
import com.jordanbunke.tdsm.data.style.Style;

public final class SetOrientationNode extends StyleStatNode {
    public static final String NAME = "set_orientation";

    public SetOrientationNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.expectExact(TypeNode.getBool()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);
        final boolean vert = (boolean) arguments.evaluate(symbolTable)[0];

        style.setAnimationOrientation(vert
                ? Orientation.VERTICAL : Orientation.HORIZONTAL);

        return FuncControlFlow.cont();
    }
}
