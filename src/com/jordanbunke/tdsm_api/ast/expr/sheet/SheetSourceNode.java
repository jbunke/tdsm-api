package com.jordanbunke.tdsm_api.ast.expr.sheet;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class SheetSourceNode extends SheetPropNode {
    public static final String NAME = "source";

    public SheetSourceNode(
            final TextPosition pos, final ExpressionNode scope
    ) {
        super(pos, scope, TypeNode.getImage());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        return getSheet(symbolTable).getSheet();
    }
}
