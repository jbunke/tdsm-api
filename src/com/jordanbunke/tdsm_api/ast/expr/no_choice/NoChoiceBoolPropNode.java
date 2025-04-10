package com.jordanbunke.tdsm_api.ast.expr.no_choice;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.layer.support.NoAssetChoice;

public final class NoChoiceBoolPropNode extends NoChoicePropNode {
    public static final String VALID = "valid", EQUAL = "equal";

    private final boolean valid;

    private NoChoiceBoolPropNode(
            final TextPosition pos, final ExpressionNode scope,
            final boolean valid
    ) {
        super(pos, scope, TypeNode.getBool());

        this.valid = valid;
    }

    public static NoChoicePropNode valid(
            final TextPosition pos, final ExpressionNode scope
    ) {
        return new NoChoiceBoolPropNode(pos, scope, true);
    }

    public static NoChoicePropNode equal(
            final TextPosition pos, final ExpressionNode scope
    ) {
        return new NoChoiceBoolPropNode(pos, scope, false);
    }

    @Override
    protected String funcName() {
        return valid ? VALID : EQUAL;
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        final NoAssetChoice nc = getNoChoice(symbolTable);

        return valid ? nc.valid : nc.equalRandomOdds;
    }
}
