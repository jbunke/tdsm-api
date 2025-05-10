package com.jordanbunke.tdsm_api.ast.expr.color_proc;

import com.jordanbunke.color_proc.ColorProc;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class ColorProcHSVNode extends ColorProcExprNode {
    public static final String NAME = "hsv";

    private ColorProcHSVNode(
            final TextPosition pos, final ExpressionNode[] args,
            final TypeNode... expectedTypes
    ) {
        super(pos, TypeNode.getColor(), args, expectedTypes);
    }

    public static ColorProcHSVNode withAlpha(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        return new ColorProcHSVNode(pos, args, TypeNode.getFloat(),
                TypeNode.getFloat(), TypeNode.getFloat(), TypeNode.getInt());
    }

    public static ColorProcHSVNode justHSV(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        return new ColorProcHSVNode(pos, args, TypeNode.getFloat(),
                TypeNode.getFloat(), TypeNode.getFloat());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final double hue = (double) vs[0], sat = (double) vs[1],
                val = (double) vs[2];

        if (vs.length == 4) {
            final int alpha = (int) vs[3];
            return ColorProc.fromHSV(hue, sat, val, alpha);
        }

        return ColorProc.fromHSV(hue, sat, val);
    }
}
