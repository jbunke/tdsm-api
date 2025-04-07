package com.jordanbunke.tdsm_api.ast.stat.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.std_lib.DefFuncExecNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm_api.util.Tokens;

public abstract class GlobalStatNode extends DefFuncExecNode {
    public GlobalStatNode(
            final TextPosition pos, final ExpressionNode[] args,
            final TypeNode... expectedTypes
    ) {
        super(new Arguments(args,
                TypeUtils.expectExact(expectedTypes)), pos);
    }

    @Override
    public String toString() {
        return "$" + Tokens.GLOBAL_NAMESPACE + "." + super.toString();
    }
}
