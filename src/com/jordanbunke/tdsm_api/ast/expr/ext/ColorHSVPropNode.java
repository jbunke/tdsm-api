package com.jordanbunke.tdsm_api.ast.expr.ext;

import com.jordanbunke.color_proc.ColorProc;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.PropertyNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

import java.awt.*;

public class ColorHSVPropNode extends PropertyNode {
    public static final String HUE = "hue", SAT = "sat", VAL = "val";

    private final String code;

    public ColorHSVPropNode(
            final TextPosition position, final ExpressionNode scope,
            final String code
    ) {
        super(position, scope, TypeNode.getColor(), TypeNode.getFloat());

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Double evaluate(final SymbolTable symbolTable) {
        final Color c = (Color) receiver.evaluate(symbolTable);

        return switch (code) {
            case HUE -> ColorProc.rgbToHue(c);
            case SAT -> ColorProc.rgbToSat(c);
            case VAL -> ColorProc.rgbToValue(c);
            default -> null;
        };
    }
}
