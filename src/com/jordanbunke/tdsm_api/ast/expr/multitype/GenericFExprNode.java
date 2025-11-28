package com.jordanbunke.tdsm_api.ast.expr.multitype;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.DefFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.Receiver;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public abstract class GenericFExprNode extends DefFuncCallNode {
    protected final Receiver receiver;

    GenericFExprNode(
            final TextPosition pos,
            final ExpressionNode scope, final TypeNode[] scopeOptions,
            final TypeNode returnType, final Arguments arguments
    ) {
        super(arguments, returnType, pos);

        receiver = new Receiver(scope, scopeOptions);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        super.semanticErrorCheck(symbolTable);
        receiver.semanticErrorCheck(symbolTable);
    }

    @Override
    public String toString() {
        return receiver + super.toString();
    }
}
