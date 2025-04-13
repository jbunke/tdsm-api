package com.jordanbunke.tdsm_api.cli.commands;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HelperFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.tdsm_api.TDSMInterpreter;
import com.jordanbunke.tdsm_api.cli.CLI;

public final class DSCommands {
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

    public static boolean processFunc(final String function) {
        final TDSMInterpreter interpreter = TDSMInterpreter.get();

        final HelperFuncNode func = interpreter.buildFunction(function);

        if (func == null)
            return true;

        final SymbolTable st = CLI.getSymbolTable();

        if (!TDSMInterpreter.check(func, st)) {
            interpreter.displayErrors();
            ScriptErrorLog.clearErrors();
        } else
            func.link(st);

        return false;
    }

    public static boolean processStat(final String statement) {
        final TDSMInterpreter interpreter = TDSMInterpreter.get();

        final StatementNode stat = interpreter.buildStatement(statement);

        if (stat == null)
            return true;

        final SymbolTable st = CLI.getSymbolTable();

        if (!TDSMInterpreter.check(stat, st)) {
            interpreter.displayErrors();
            ScriptErrorLog.clearErrors();
        } else
            stat.execute(st);

        return false;
    }
}
