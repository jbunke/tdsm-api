package com.jordanbunke.tdsm_api.ast.expr.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.MemberFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.StyleTypeNode;

public abstract class StyleExprNode extends MemberFuncCallNode {
    public StyleExprNode(
            final TextPosition pos, final ExpressionNode scope,
            final TypeNode returnType, final ExpressionNode[] args,
            final TypeNode... expectedArgs
    ) {
        super(pos, scope, StyleTypeNode.get(), returnType,
                args, TypeUtils.expectExact(expectedArgs));
    }

    protected final Style getStyle(final SymbolTable symbolTable) {
        return (Style) receiver.evaluate(symbolTable);
    }
}
