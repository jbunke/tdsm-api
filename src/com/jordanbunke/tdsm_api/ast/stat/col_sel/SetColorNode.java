package com.jordanbunke.tdsm_api.ast.stat.col_sel;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.util.UpdateChecker;

import java.awt.*;

public final class SetColorNode extends ColSelStatNode {
    public static final String NAME = "set_color";

    public SetColorNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeNode.getColor());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final ColorSelection cs = getColSel(symbolTable);
        final Color color = (Color) arguments.evaluate(symbolTable)[0];

        cs.setColor(color, false);
        UpdateChecker.get().ping(cs);

        return FuncControlFlow.cont();
    }
}
