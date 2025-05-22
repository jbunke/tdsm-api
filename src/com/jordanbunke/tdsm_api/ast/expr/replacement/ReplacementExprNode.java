package com.jordanbunke.tdsm_api.ast.expr.replacement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.MemberFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.Replacement;
import com.jordanbunke.tdsm_api.ast.type.ReplacementTypeNode;

public abstract class ReplacementExprNode extends MemberFuncCallNode {
    public ReplacementExprNode(
            final TextPosition pos, final ExpressionNode scope,
            final TypeNode returnType, final ExpressionNode[] args,
            final TypeNode... expectedArgs
    ) {
        super(pos, scope, ReplacementTypeNode.get(), returnType,
                args, TypeUtils.expectExact(expectedArgs));
    }

    protected final Replacement getReplacement(final SymbolTable symbolTable) {
        return (Replacement) receiver.evaluate(symbolTable);
    }
}
