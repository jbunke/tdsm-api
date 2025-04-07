package com.jordanbunke.tdsm_api.ast.expr.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.DefFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm_api.util.Tokens;

public abstract class GlobalExprNode extends DefFuncCallNode {
    public GlobalExprNode(
            final TextPosition pos, final TypeNode returnType,
            final ExpressionNode[] args, final TypeNode... expectedTypes
    ) {
        this(pos, returnType, new Arguments(args,
                TypeUtils.expectExact(expectedTypes)));
    }

    public GlobalExprNode(
            final TextPosition pos, final TypeNode returnType,
            final Arguments arguments
    ) {
        super(arguments, returnType, pos);
    }

    @Override
    public String toString() {
        return "$" + Tokens.GLOBAL_NAMESPACE + "." + super.toString();
    }
}
