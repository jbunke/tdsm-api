package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.style.Style;

public final class IsStyleFlagNode extends StyleExprNode {
    public static final String WRAP = "is_wrap_anims_across_dims",
            MULTIPLE = "is_multiple_anims_per_dim",
            SINGLE = "is_all_anims_single_dim";

    private final String code;

    public IsStyleFlagNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final String code
    ) {
        super(pos, scope, TypeNode.getBool(), args);

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        final Style style = getStyle(symbolTable);

        return switch (code) {
            case SINGLE -> style.isSingleDim();
            case MULTIPLE -> style.isMultipleAnimsPerDim();
            case WRAP -> style.isWrapAnimsAcrossDims();
            default -> null;
        };
    }
}
