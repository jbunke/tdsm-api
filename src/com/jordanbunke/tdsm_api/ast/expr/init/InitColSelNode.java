package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;

import java.awt.*;

public final class InitColSelNode extends InitExprNode {
    public static final String NAME = "col_sel";

    public InitColSelNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, ColSelTypeNode.get(), args, TypeNode.getString(),
                TypeNode.getBool(), TypeNode.arrayOf(TypeNode.getColor()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public ColorSelection evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final String name = (String) vs[0];
        final boolean anyColor = (boolean) vs[1];
        final Color[] swatches = ((ScriptArray) vs[2]).stream()
                .map(o -> (Color) o).toArray(Color[]::new);

        if (name.isEmpty()) {
            ScriptErrorLog.runtimeError(arguments.get(0).getPosition(),
                    "Color selection name must be non-empty");
            return null;
        }

        return new ColorSelection(name, anyColor, swatches);
    }
}
