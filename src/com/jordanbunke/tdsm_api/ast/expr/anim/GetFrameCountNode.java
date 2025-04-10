package com.jordanbunke.tdsm_api.ast.expr.anim;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class GetFrameCountNode extends AnimExprNode {
    public static final String NAME = "get_frame_count";

    public GetFrameCountNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.getInt(), args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return getAnim(symbolTable).frameCount();
    }
}
