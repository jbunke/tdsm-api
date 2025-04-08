package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.TDSMInterpreter;

public final class SetFramesPerDimNode extends StyleStatNode {
    public static final String NAME = "set_frames_per_dim";

    public SetFramesPerDimNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.expectExact(TypeNode.getInt()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);
        final int fpd = (int) arguments.evaluate(symbolTable)[0],
                min = style.longestAnimFrameCount();

        if (fpd < style.longestAnimFrameCount())
            TDSMInterpreter.failure(
                    "Couldn't set frames per dimension",
                    "the value to be assigned (" + fpd +
                            ") is lower than the minimum allowed (" + min + ")",
                    arguments.get(0).getPosition());
        else
            style.setFramesPerDim(fpd);

        return FuncControlFlow.cont();
    }
}
