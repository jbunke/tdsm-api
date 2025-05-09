package com.jordanbunke.tdsm_api.ast.expr.replacement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.DynamicFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FlexParamFunc;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Replacement;

import java.awt.*;

public final class ReplacementFuncNode extends ReplacementPropNode {
    public static final String NAME = "func";

    public ReplacementFuncNode(
            final TextPosition pos, final ExpressionNode scope
    ) {
        super(pos, scope, new FuncTypeNode(
                new TypeNode[] { TypeNode.getColor() }, TypeNode.getColor()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Replacement r = getReplacement(symbolTable);

        return new DynamicFuncNode<>(new TypeNode[] { TypeNode.getColor() },
                TypeNode.getColor(), FlexParamFunc.one(r.func(), Color.class));
    }
}
