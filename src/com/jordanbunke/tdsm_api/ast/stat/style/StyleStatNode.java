package com.jordanbunke.tdsm_api.ast.stat.style;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.std_lib.MemberFuncExecNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm.data.style.Style;
import com.jordanbunke.tdsm_api.ast.type.StyleTypeNode;

public abstract class StyleStatNode extends MemberFuncExecNode {
    public StyleStatNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final TypeNode[]... expectedArgTypes
    ) {
//        super(pos, scope, StyleTypeNode.get(),
//                args, TypeUtils.expectExact(expectedArgTypes));
        super(pos, scope, StyleTypeNode.get(),
                args, expectedArgTypes);
    }

    protected final Style getStyle(final SymbolTable symbolTable) {
        return (Style) receiver.evaluate(symbolTable);
    }
}
