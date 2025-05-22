package com.jordanbunke.tdsm_api.ast.stat.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.std_lib.DefFuncExecNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm_api.util.Tokens;

public abstract class UtilStatNode extends DefFuncExecNode {
    public UtilStatNode(
            final TextPosition pos, final ExpressionNode[] args,
            final TypeNode... expectedTypes
    ) {
        super(new Arguments(args,
                TypeUtils.expectExact(expectedTypes)), pos);
    }

    @Override
    public String toString() {
        return "$" + Tokens.UTIL_NAMESPACE + "." + super.toString();
    }
}
