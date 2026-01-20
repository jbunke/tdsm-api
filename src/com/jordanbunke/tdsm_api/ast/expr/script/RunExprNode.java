package com.jordanbunke.tdsm_api.ast.expr.script;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm_api.util.TDSMScript;

public final class RunExprNode extends ScriptExprNode {
    public static final String NAME = "run";

    public RunExprNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, TypeNode.wildcard(), args);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        receiver.semanticErrorCheck(symbolTable);

        for (ExpressionNode arg : arguments.args())
            arg.semanticErrorCheck(symbolTable);
    }

    @Override
    public Object evaluate(SymbolTable symbolTable) {
        final TDSMScript script = getScript(symbolTable);
        final Object[] args = arguments.evaluate(symbolTable);

        // execute before every internal script execution
        final SymbolTable scriptTable =
                SymbolTable.root(script.head(), script.path());
        script.head().semanticErrorCheck(scriptTable);

        return ScriptErrorLog.hasNoErrors()
                ? script.head().execute(scriptTable, args) : null;
    }

    @Override
    protected String funcName() {
        return NAME;
    }
}
