package com.jordanbunke.tdsm_api.ast.expr.col_sel;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;

import java.util.Arrays;

public final class ColSelSwatchesNode extends ColSelPropNode {
    public static final String NAME = "swatches";

    public ColSelSwatchesNode(
            final TextPosition pos, final ExpressionNode scope
    ) {
        super(pos, scope, TypeNode.arrayOf(TypeNode.getColor()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public ScriptArray evaluate(final SymbolTable symbolTable) {
        final ColorSelection cs = getColSel(symbolTable);

        return new ScriptArray(Arrays.stream(cs.getSwatches()));
    }
}
