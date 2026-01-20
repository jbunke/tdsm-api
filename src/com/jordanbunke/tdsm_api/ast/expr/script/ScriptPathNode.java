package com.jordanbunke.tdsm_api.ast.expr.script;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public final class ScriptPathNode extends ScriptPropNode {
    public static final String NAME = "path";

    public ScriptPathNode(final TextPosition pos, final ExpressionNode scope) {
        super(pos, scope, TypeNode.getString());
    }

    @Override
    public String evaluate(final SymbolTable symbolTable) {
        return getScript(symbolTable).path().toString();
    }

    @Override
    protected String funcName() {
        return NAME;
    }
}
