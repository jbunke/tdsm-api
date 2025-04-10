package com.jordanbunke.tdsm_api.ast.stat.col_sel;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.std_lib.MemberFuncExecNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm.data.layer.support.ColorSelection;
import com.jordanbunke.tdsm_api.ast.type.ColSelTypeNode;

public abstract class ColSelStatNode extends MemberFuncExecNode {
    public ColSelStatNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final TypeNode... expectedArgTypes
    ) {
        super(pos, scope, ColSelTypeNode.get(),
                args, TypeUtils.expectExact(expectedArgTypes));
    }

    protected final ColorSelection getColSel(final SymbolTable symbolTable) {
        return (ColorSelection) receiver.evaluate(symbolTable);
    }
}
