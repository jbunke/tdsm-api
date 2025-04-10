package com.jordanbunke.tdsm_api.ast.expr.anim;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.MemberFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.Animation;
import com.jordanbunke.tdsm_api.ast.type.AnimTypeNode;

public abstract class AnimExprNode extends MemberFuncCallNode {
    public AnimExprNode(
            final TextPosition pos, final ExpressionNode scope,
            final TypeNode returnType, final ExpressionNode[] args,
            final TypeNode... expectedArgs
    ) {
        super(pos, scope, AnimTypeNode.get(), returnType,
                args, TypeUtils.expectExact(expectedArgs));
    }

    protected final Animation getAnim(final SymbolTable symbolTable) {
        return (Animation) receiver.evaluate(symbolTable);
    }
}
