package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.style.Style;

public class ResetCustomizationNode extends StyleStatNode {
    public static final String NAME = "reset_custom";

    public ResetCustomizationNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args, TypeUtils.expectExact());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);
        style.resetCustomization();

        return FuncControlFlow.cont();
    }
}
