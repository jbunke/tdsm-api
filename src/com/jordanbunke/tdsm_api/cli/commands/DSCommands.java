package com.jordanbunke.tdsm_api.cli.commands;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.tdsm_api.TDSMInterpreter;
import com.jordanbunke.tdsm_api.cli.CLI;

public final class DSCommands {
    public static final String DNP = "%dnp%";

    public static boolean processEval(final String expression) {
        final TDSMInterpreter interpreter = TDSMInterpreter.get();

        final ExpressionNode expr = interpreter.buildExpression(expression);

        if (expr == null)
            return true;

        final SymbolTable st = CLI.getSymbolTable();

        if (!TDSMInterpreter.check(expr, st)) {
            interpreter.displayErrors();
            ScriptErrorLog.clearErrors();
        } else {
            final Object value = expr.evaluate(st);
            CLI.writeGreyLine(" = " + value);
        }

        return false;
    }
}
