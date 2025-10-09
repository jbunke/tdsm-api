package com.jordanbunke.tdsm_api.ast.expr.script;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.std_lib.MemberFuncCallNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeUtils;
import com.jordanbunke.tdsm_api.ast.type.ScriptTypeNode;
import com.jordanbunke.tdsm_api.util.TDSMScript;

public abstract class ScriptExprNode extends MemberFuncCallNode {
    public ScriptExprNode(
            final TextPosition pos, final ExpressionNode scope,
            final TypeNode returnType, final ExpressionNode[] args,
            final TypeNode... expectedArgs
    ) {
        super(pos, scope, ScriptTypeNode.get(), returnType,
                args, TypeUtils.expectExact(expectedArgs));
    }

    protected final TDSMScript getScript(final SymbolTable symbolTable) {
        return (TDSMScript) receiver.evaluate(symbolTable);
    }
}
