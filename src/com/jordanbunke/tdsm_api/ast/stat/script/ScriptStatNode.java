package com.jordanbunke.tdsm_api.ast.stat.script;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.std_lib.MemberFuncExecNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm_api.ast.type.ScriptTypeNode;
import com.jordanbunke.tdsm_api.util.TDSMScript;

public abstract class ScriptStatNode extends MemberFuncExecNode {
    public ScriptStatNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args, final TypeNode[]... expectedArgTypes
    ) {
        super(pos, scope, ScriptTypeNode.get(),
                args, expectedArgTypes);
    }

    protected final TDSMScript getScript(final SymbolTable symbolTable) {
        return (TDSMScript) receiver.evaluate(symbolTable);
    }
}
