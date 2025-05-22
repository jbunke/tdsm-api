package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.ChildFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.Replacement;
import com.jordanbunke.tdsm_api.ast.type.ReplacementTypeNode;
import com.jordanbunke.tdsm_api.util.MetaFuncHelper;

import java.awt.*;

public final class InitReplacementNode extends InitExprNode {
    public static final String NAME = "replacement";

    public InitReplacementNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, ReplacementTypeNode.get(), args, TypeNode.getInt(),
                new FuncTypeNode(new TypeNode[] { TypeNode.getColor() },
                        TypeNode.getColor()));
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Replacement evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.evaluate(symbolTable);

        final int index = (int) vs[0];
        final ChildFuncNode funcSource = (ChildFuncNode) vs[1];

        return new Replacement(index, c ->
                MetaFuncHelper.evaluate(funcSource, symbolTable, Color.class,
                arguments.get(1).getPosition(), c));
    }
}
