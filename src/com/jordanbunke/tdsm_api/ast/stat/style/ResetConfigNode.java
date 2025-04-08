package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.style.Style;

public final class ResetConfigNode extends StyleStatNode {
    public static final String
            RESET_LAYOUT = "reset_layout",
            RESET_PADDING = "reset_padding",
            RESET_SEQUENCING = "reset_sequencing";

    private final String code;

    public ResetConfigNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final String code
    ) {
        super(pos, scope, args, TypeUtils.expectExact());

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        switch (code) {
            case RESET_LAYOUT -> style.resetLayout();
            case RESET_PADDING -> style.resetPadding();
            case RESET_SEQUENCING -> style.resetSequencing();
        }

        return FuncControlFlow.cont();
    }
}
