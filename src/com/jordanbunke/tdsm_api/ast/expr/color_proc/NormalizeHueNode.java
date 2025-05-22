package com.jordanbunke.tdsm_api.ast.expr.color_proc;

import com.jordanbunke.color_proc.ColorProc;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class NormalizeHueNode extends ColorProcExprNode {
    public static final String NAME = "normalize_hue";

    public NormalizeHueNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, TypeNode.getFloat(), args, TypeNode.getFloat());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Double evaluate(final SymbolTable symbolTable) {
        final double hue = (double) arguments.get(0).evaluate(symbolTable);
        return ColorProc.normalizeHue(hue);
    }
}
