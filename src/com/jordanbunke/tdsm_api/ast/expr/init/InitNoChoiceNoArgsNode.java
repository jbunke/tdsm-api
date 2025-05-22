package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.NoAssetChoice;
import com.jordanbunke.tdsm_api.ast.type.NoChoiceTypeNode;

public final class InitNoChoiceNoArgsNode extends InitExprNode {
    public static final String EQUAL = "no_choice_equal",
            INVALID = "no_choice_invalid";

    private final String code;

    public InitNoChoiceNoArgsNode(
            final TextPosition pos, final ExpressionNode[] args,
            final String code
    ) {
        super(pos, NoChoiceTypeNode.get(), args);

        this.code = code;
    }

    @Override
    protected String funcName() {
        return code;
    }

    @Override
    public NoAssetChoice evaluate(final SymbolTable symbolTable) {
        return EQUAL.equals(code)
                ? NoAssetChoice.equal()
                : NoAssetChoice.invalid();
    }
}
