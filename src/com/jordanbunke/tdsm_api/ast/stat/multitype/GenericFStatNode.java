package com.jordanbunke.tdsm_api.ast.stat.multitype;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.std_lib.DefFuncExecNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.Arguments;
import com.jordanbunke.delta_time.scripting.util.Receiver;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public abstract class GenericFStatNode extends DefFuncExecNode {
    protected final Receiver receiver;

    GenericFStatNode(
            final TextPosition pos,
            final ExpressionNode scope, final TypeNode[] scopeOptions,
            final Arguments arguments
    ) {
        super(arguments, pos);

        receiver = new Receiver(scope, scopeOptions);
    }

    @Override
    public void semanticErrorCheck(SymbolTable symbolTable) {
        super.semanticErrorCheck(symbolTable);
        receiver.semanticErrorCheck(symbolTable);
    }

    @Override
    public String toString() {
        return receiver + super.toString();
    }
}
