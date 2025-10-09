package com.jordanbunke.tdsm_api.ast.stat.script;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm_api.util.TDSMScript;

public final class RunStatNode extends ScriptStatNode {
    public static final String NAME = "run";

    public RunStatNode(
            final TextPosition pos, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(pos, scope, args);
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final TDSMScript script = getScript(symbolTable);
        final Object[] args = arguments.evaluate(symbolTable);

        // execute before every internal script execution
        final SymbolTable scriptTable =
                SymbolTable.root(script.head(), script.path());
        script.head().semanticErrorCheck(scriptTable);

        if (ScriptErrorLog.hasNoErrors())
            script.head().execute(scriptTable, args);

        return FuncControlFlow.cont();
    }
}
