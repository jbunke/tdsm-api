package com.jordanbunke.tdsm_api.ast.expr.no_choice;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.MemberFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.layer.support.NoAssetChoice;
import com.jordanbunke.tdsm_api.ast.type.NoChoiceTypeNode;

public abstract class NoChoiceExprNode extends MemberFuncCallNode {
    public NoChoiceExprNode(
            final TextPosition pos, final ExpressionNode scope,
            final TypeNode returnType, final ExpressionNode[] args,
            final TypeNode... expectedArgs
    ) {
        super(pos, scope, NoChoiceTypeNode.get(), returnType,
                args, TypeUtils.expectExact(expectedArgs));
    }

    protected final NoAssetChoice getNoChoice(final SymbolTable symbolTable) {
        return (NoAssetChoice) receiver.evaluate(symbolTable);
    }
}
