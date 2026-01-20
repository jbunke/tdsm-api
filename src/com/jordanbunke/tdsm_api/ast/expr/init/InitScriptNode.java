package com.jordanbunke.tdsm_api.ast.expr.init;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.PathHelper;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.tdsm_api.TDSMInterpreter;
import com.jordanbunke.tdsm_api.ast.type.ScriptTypeNode;
import com.jordanbunke.tdsm_api.util.TDSMScript;

import java.nio.file.Path;

public final class InitScriptNode extends InitExprNode {
    public static final String NAME = "script";

    public InitScriptNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, ScriptTypeNode.get(), args, TypeNode.getString());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public TDSMScript evaluate(final SymbolTable symbolTable) {
        final ExpressionNode arg = arguments.get(0);
        final String scriptFP = (String) arg.evaluate(symbolTable);
        final Path scriptPath = PathHelper.process(
                scriptFP, symbolTable, arg.getPosition());
        final String content = FileIO.readFile(scriptPath);

        if (content == null)
            ScriptErrorLog.runtimeError(arg.getPosition(),
                    "Failed to read a script file at path \"" +
                            PathHelper.formatPathString(scriptFP) + "\"");

        final HeadFuncNode script = TDSMInterpreter.get().build(content);
        script.semanticErrorCheck(SymbolTable.root(script, scriptPath));

        if (ScriptErrorLog.hasNoErrors())
            return new TDSMScript(script, scriptPath);

        return null;
    }
}
