package com.jordanbunke.tdsm_api.ast.stat.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.json.JSONPair;
import com.jordanbunke.json.JSONReader;
import com.jordanbunke.tdsm.util.JSONHelper;
import com.jordanbunke.tdsm_api.TDSMInterpreter;

import java.util.List;

public final class LoadFromJSONNode extends GlobalStatNode {
    public static final String NAME = "load_from_json";

    public LoadFromJSONNode(
            final TextPosition pos, final ExpressionNode[] args
    ) {
        super(pos, args, TypeNode.getString());
    }

    @Override
    protected String funcName() {
        return NAME;
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final String contents = (String) arguments.get(0).evaluate(symbolTable);

        final JSONPair[] pairs = JSONReader.readObject(contents);
        final List<String> errors = JSONHelper.loadFromJSON(pairs, false);

        for (String error : errors)
            TDSMInterpreter.failure(error, arguments.get(0).getPosition());

        return FuncControlFlow.cont();
    }
}
