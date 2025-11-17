package com.jordanbunke.tdsm_api.ast.stat.col_sel;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.util.UpdateChecker;

import java.awt.*;

public final class SetFromSwatchNode extends ColSelStatNode {
    public static final String NAME = "set_from_swatch";

    public SetFromSwatchNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeNode.getInt());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final ColorSelection cs = getColSel(symbolTable);
        final int index = (Integer) arguments.evaluate(symbolTable)[0];

        final Color[] swatches = cs.getSwatches();

        if (index < 0 || index >= swatches.length)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.get(0).getPosition(),
                    "The index (" + index + ") is invalid for the " +
                            "number of swatches in the color selection '" +
                            receiver.receiver() + "' (" +
                            swatches.length + ")");
        else {
            cs.setColor(swatches[index], false);
            UpdateChecker.get().ping(cs);
        }

        return FuncControlFlow.cont();
    }
}
