package com.jordanbunke.tdsm_api.ast.expr.col_sel;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.MemberFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;

public abstract class ColSelExprNode extends MemberFuncCallNode {
    public ColSelExprNode(
            final TextPosition pos, final ExpressionNode scope,
            final TypeNode returnType, final ExpressionNode[] args,
            final TypeNode... expectedArgs
    ) {
        super(pos, scope, ColSelTypeNode.get(), returnType,
                args, TypeUtils.expectExact(expectedArgs));
    }

    protected final ColorSelection getColSel(final SymbolTable symbolTable) {
        return (ColorSelection) receiver.evaluate(symbolTable);
    }
}
